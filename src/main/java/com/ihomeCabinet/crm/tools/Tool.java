package com.ihomeCabinet.crm.tools;

import java.security.SecureRandom;
import java.util.Base64;

public class Tool {
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes = 128 bits
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
