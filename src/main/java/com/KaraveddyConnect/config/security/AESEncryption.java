package com.KaraveddyConnect.config.security;

import com.KaraveddyConnect.aspects.interfaces.LogException;
import com.KaraveddyConnect.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 18:04 PM
 **/
@Slf4j
public class AESEncryption {
    public static SecretKey generateAESKeyFromSharedSecret(String sharedSecretKey) {
        byte[] sharedSecret = sharedSecretKey.getBytes();
        byte[] keyBytes = new byte[16];
        System.arraycopy(sharedSecret, 0, keyBytes, 0, Math.min(sharedSecret.length, keyBytes.length));
        return new SecretKeySpec(keyBytes, "AES");
    }

    @LogException
    public static String encryptionHandler(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKey secretKey = generateAESKeyFromSharedSecret(key);
            byte[] iv = new byte[12];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(1, secretKey, parameterSpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            byte[] encryptedDataWithIv = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
            System.arraycopy(encryptedData, 0, encryptedDataWithIv, iv.length, encryptedData.length);
            return Base64.getEncoder().encodeToString(encryptedDataWithIv);
        } catch (Exception e) {
            return "";
        }
    }

    @LogException
    public static String decryptionHandler(String encryptedData, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKey secretKey = generateAESKeyFromSharedSecret(key);
            byte[] encryptedDataWithIv = Base64.getDecoder().decode(encryptedData);
            byte[] iv = new byte[12];
            System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(2, secretKey, parameterSpec);
            byte[] encryptedDataBytes = new byte[encryptedDataWithIv.length - iv.length];
            System.arraycopy(encryptedDataWithIv, iv.length, encryptedDataBytes, 0, encryptedDataBytes.length);
            byte[] decryptedData = cipher.doFinal(encryptedDataBytes);
            return new String(decryptedData);
        } catch (Exception e) {
            throw new BadRequestException("unable to encrypt data");
        }
    }
}