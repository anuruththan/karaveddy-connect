package com.KaraveddyConnect.service.impl;

import com.KaraveddyConnect.config.security.AESEncryption;
import com.KaraveddyConnect.service.EncryptionDecryptionService;
import com.KaraveddyConnect.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 18:03 PM
 **/
@Service
@Slf4j
public class EncryptionDecryptionServiceImpl implements EncryptionDecryptionService {

    @Value("${spring.aes.encrption.key}")
    private String encryptionKey;

    @Override
    public Optional<?> decrypt(String encryptedPayload, Class<?> targetClass) {
        log.info("decrypt function invoked with payload : {} , target class : {}, key : {}", encryptedPayload, targetClass, encryptionKey);
        String decryptedString = AESEncryption.decryptionHandler(encryptedPayload, encryptionKey);
        log.info("decrypt decoded string {}", decryptedString);
        return CommonUtil.fromJSON(decryptedString, targetClass);
    }

    @Override
    public String encrypt(Object plainPayload) {
        String json = CommonUtil.toJSON(plainPayload);
        String encryptedPayload = AESEncryption.encryptionHandler(json, encryptionKey);
        return encryptedPayload;
    }
}
