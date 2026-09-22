package com.KaraveddyConnect.service;

import java.util.Optional;

public interface EncryptionDecryptionService {

    /**
     * decrypt
     *
     * @param encryptedPayload of {@link String}
     * @param targetClass      of {@link Class}
     * @return Optional
     */
    Optional<?> decrypt(String encryptedPayload, Class<?> targetClass);

    /**
     * encrypt
     *
     * @param plainPayload of {@link Object}
     * @return String
     */
    String encrypt(Object plainPayload);
}
