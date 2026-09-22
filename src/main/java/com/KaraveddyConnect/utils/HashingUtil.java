package com.KaraveddyConnect.utils;

import com.KaraveddyConnect.aspects.interfaces.LogException;
import com.KaraveddyConnect.exception.BusinessException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:45 PM
 **/
public class HashingUtil {

    private static final char[] hexArray = "0123456789abcdef".toCharArray();

    public static String generateHash(String strToHash) throws UnsupportedEncodingException, IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ByteArrayOutputStream pwsalt = new ByteArrayOutputStream();
        pwsalt.write(strToHash.getBytes(StandardCharsets.UTF_8));
        byte[] unhashedBytes = pwsalt.toByteArray();
        byte[] digestVonPassword = md.digest(unhashedBytes);
        return bytesToHex(digestVonPassword);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars);
    }

    @LogException
    public static String generateHashForClient(String strToHash) {
        try {
            return generateHash(strToHash);
        } catch (Exception var5) {
            throw new BusinessException("unable to generate hash for client");
        }
    }

}

