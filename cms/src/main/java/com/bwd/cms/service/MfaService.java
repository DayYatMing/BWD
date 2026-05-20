package com.bwd.cms.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MfaService {

    private final Map<String, MfaCode> mfaCodes = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private static final Logger LOG = LoggerFactory.getLogger(MfaService.class);

    public MfaCode generateCode(String username) {
        String code;

        //by-pass user for automation testing with 2fa
        if ("user".equalsIgnoreCase(username) || "admin".equalsIgnoreCase(username)) {
            code = String.format("%06d", 123456);
        } else {
            code = String.format("%06d", random.nextInt(999999));
        }
        MfaCode mfaCode = new MfaCode(code, LocalDateTime.now().plusMinutes(5));
        mfaCodes.put(username, mfaCode);
        return mfaCode;
    }

    public boolean verifyCode(String username, String code) {
        MfaCode mfaCode = mfaCodes.get(username);
        if (mfaCode == null) {
            return false;
        }
        if (mfaCode.isExpired()) {
            mfaCodes.remove(username);
            return false;
        }
        if (mfaCode.getCode().equals(code)) {
            mfaCodes.remove(username);
            return true;
        }
        return false;
    }

    public static class MfaCode {

        private final String code;
        private final LocalDateTime expiry;

        public MfaCode(String code, LocalDateTime expiry) {
            this.code = code;
            this.expiry = expiry;
        }

        public String getCode() {
            return code;
        }

        public boolean isExpired() {
            return expiry.isBefore(LocalDateTime.now());
        }
    }
}
