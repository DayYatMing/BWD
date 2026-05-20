package com.bwd.cms.web.rest;

import static com.bwd.cms.security.SecurityUtils.AUTHORITIES_CLAIM;
import static com.bwd.cms.security.SecurityUtils.JWT_ALGORITHM;
import static com.bwd.cms.security.SecurityUtils.USER_ID_CLAIM;

import com.bwd.cms.domain.ClientIpAddress;
import com.bwd.cms.domain.MfaVerify;
import com.bwd.cms.domain.User;
import com.bwd.cms.repository.ClientIpAddressRepository;
import com.bwd.cms.repository.UserRepository;
import com.bwd.cms.security.DomainUserDetailsService.UserWithId;
import com.bwd.cms.service.MfaService;
import com.bwd.cms.service.UserService;
import com.bwd.cms.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AuthenticateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateController.class);
    private final JwtEncoder jwtEncoder;
    private final MfaService mfaService;
    private final JavaMailSender mailSender;
    private final UserService userService;
    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    ClientIpAddressRepository clientIpAddressRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    public AuthenticateController(
        JwtEncoder jwtEncoder,
        ReactiveAuthenticationManager authenticationManager,
        MfaService mfaService,
        JavaMailSender mailSender,
        UserService userService
    ) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.mfaService = mfaService;
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public Mono<ResponseEntity<Map<String, String>>> authorize(@Valid @RequestBody Mono<LoginVM> loginVM, ServerHttpRequest request) {
        String ipAddress = Optional.ofNullable(request.getHeaders().getFirst("X-Forwarded-For"))
            .map(x -> x.split(",")[0])
            .orElseGet(() -> {
                InetSocketAddress remote = request.getRemoteAddress();
                if (remote == null) return "unknown";
                String host = remote.getAddress().getHostAddress();
                return host.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : host;
            });

        return loginVM.flatMap(login -> {
            ClientIpAddress audit = new ClientIpAddress();
            audit.setLogin(login.getUsername());
            audit.setIpAddress(ipAddress);
            audit.setLoginSuccess(false);
            audit.setAttemptTime(LocalDateTime.now());
            return clientIpAddressRepository
                .save(audit)
                .flatMap(savedAudit ->
                    authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
                        .flatMap(auth -> {
                            savedAudit.setLoginSuccess(true);
                            return clientIpAddressRepository
                                .save(savedAudit)
                                .flatMap(updatedAudit -> {
                                    MfaService.MfaCode mfaCodeObj = mfaService.generateCode(login.getUsername());

                                    //by-pass user for automation testing without sending email
                                    if ("user".equalsIgnoreCase(login.getUsername()) || "admin".equalsIgnoreCase(login.getUsername())) {
                                        return Mono.just(
                                            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                                                Map.of(
                                                    "status",
                                                    "MFA_REQUIRED",
                                                    "username",
                                                    login.getUsername(),
                                                    "mfa_code",
                                                    mfaCodeObj.getCode()
                                                )
                                            )
                                        );
                                    }

                                    return sendMfaEmail(login.getUsername(), mfaCodeObj.getCode()).then(
                                        Mono.just(
                                            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                                                Map.of(
                                                    "status",
                                                    "MFA_REQUIRED",
                                                    "username",
                                                    login.getUsername(),
                                                    "mfa_code",
                                                    mfaCodeObj.getCode()
                                                )
                                            )
                                        )
                                    );
                                });
                        })
                );
        });
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Void> isAuthenticated(Principal principal) {
        LOG.debug("REST request to check if the current user is authenticated");
        return ResponseEntity.status(principal == null ? HttpStatus.UNAUTHORIZED : HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/mfa-verify")
    public Mono<ResponseEntity<JWTToken>> verifyMfa(@RequestBody MfaVerify mfaVerifyVM) {
        boolean valid = mfaService.verifyCode(mfaVerifyVM.getUsername(), mfaVerifyVM.getCode());

        if (!valid) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        return userService
            .getUserWithAuthoritiesByLogin(mfaVerifyVM.getUsername())
            .map(user -> {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getLogin(),
                    null,
                    user.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getName())).collect(Collectors.toList())
                );
                String jwt = createToken(authentication, false);

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(jwt);

                return new ResponseEntity<>(new JWTToken(jwt), headers, HttpStatus.OK);
            });
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        Instant now = Instant.now();
        Instant validity = now.plus(
            rememberMe ? this.tokenValidityInSecondsForRememberMe : this.tokenValidityInSeconds,
            ChronoUnit.SECONDS
        );
        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim(AUTHORITIES_CLAIM, authorities);

        if (authentication.getPrincipal() instanceof UserWithId user) {
            builder.claim(USER_ID_CLAIM, user.getId());
        }
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, builder.build())).getTokenValue();
    }

    public Mono<Void> sendMfaEmail(String username, String code) {
        return userRepository
            .findOneByLogin(username)
            .flatMap(user -> {
                String recipientEmail = user.getEmail();

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("no-reply@bw-digital.com");
                message.setTo(recipientEmail);
                message.setSubject("Your Security Verification Code");
                String content =
                    "Hi " +
                    username +
                    ",\n\n" +
                    "To continue signing in, please use the security verification code below:\n\n" +
                    "Security Code: " +
                    code +
                    "\n\n" +
                    "This code is valid for 5 minutes. If it expires, log in again to request a new one.\n\n" +
                    "If you did not request this code, please ignore this email.\n\n" +
                    "Best regards,\n" +
                    "Hawaiki Cable";
                message.setText(content);

                try {
                    mailSender.send(message);
                } catch (Exception e) {
                    return Mono.error(new RuntimeException("Failed to send MFA code"));
                }

                return Mono.empty();
            });
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
