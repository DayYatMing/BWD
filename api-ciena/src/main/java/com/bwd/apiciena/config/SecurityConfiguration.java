package com.bwd.apiciena.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

import com.bwd.apiciena.security.AuthoritiesConstants;
import com.bwd.apiciena.web.filter.SpaWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    private final JHipsterProperties jHipsterProperties;

    public SecurityConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
            userDetailsService
        );
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .securityMatcher(
                new NegatedServerWebExchangeMatcher(
                    new OrServerWebExchangeMatcher(pathMatchers("/", "/app/**", "/i18n/**", "/content/**", "/swagger-ui/**"))
                )
            )
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .addFilterAfter(new SpaWebFilter(), SecurityWebFiltersOrder.HTTPS_REDIRECT)
            .headers(headers ->
                headers
                    .contentSecurityPolicy(csp -> csp.policyDirectives(jHipsterProperties.getSecurity().getContentSecurityPolicy()))
                    .frameOptions(frameOptions -> frameOptions.mode(Mode.DENY))
                    .referrerPolicy(referrer ->
                        referrer.policy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                    )
                    .permissionsPolicy(permissions ->
                        permissions.policy(
                            "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"
                        )
                    )
            )
            .authorizeExchange(authz ->
                // prettier-ignore
                authz
                    .pathMatchers("/").permitAll()
                    .pathMatchers("/*.*").permitAll()
                    .pathMatchers("/api/bwd/**").authenticated()
                    .pathMatchers("/api/authenticate").permitAll()
                    .pathMatchers("/api/register").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/api/activate").permitAll()
                    .pathMatchers("/api/account/reset-password/init").permitAll()
                    .pathMatchers("/api/account/reset-password/finish").permitAll()
                    .pathMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/api/users/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/api/authorities/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/api/gateway/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/api/**").authenticated()
                    .pathMatchers("/services/*/management/health/readiness").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/services/*/v3/api-docs").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/services/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/v3/api-docs/bwd").authenticated()
                    .pathMatchers("/v3/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/management/health").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/management/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/management/info").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/management/prometheus").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            )
            .httpBasic(basic -> basic.disable())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
        return http.build();
    }
}
