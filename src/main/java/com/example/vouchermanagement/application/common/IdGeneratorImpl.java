package com.example.vouchermanagement.application.common;

import java.security.SecureRandom;

public class IdGeneratorImpl implements IdGenerator {
    private static final int ID_LENGTH = 10;
    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String nextId() {
        byte[] randomBytes = new byte[ID_LENGTH];
        SECURE_RANDOM.nextBytes(randomBytes);
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = randomBytes[i] & 0x3F; // 0x3F = 00111111 binary
            sb.append(ALPHANUMERIC.charAt(randomIndex % ALPHANUMERIC.length()));
        }
        return sb.toString().toUpperCase();
    }
}
