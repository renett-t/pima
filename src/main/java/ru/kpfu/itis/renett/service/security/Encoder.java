package ru.kpfu.itis.renett.service.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder implements EncoderInterface {
    private final String hashingAlgorithm;
    private final String salt;

    public Encoder(String hashingAlgorithm) {
        this.hashingAlgorithm = hashingAlgorithm;
        this.salt = "pIMa";
    }

    @Override
    public String encodeString(String str) {
        String hashedString = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashingAlgorithm);
            messageDigest.update(getSaltFromInputString(str));
            byte[] hashedBytes = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            hashedString = getStringFromBytes(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return hashedString;
    }

    @Override
    public boolean validate(String value1, String value2) {
        return (encodeString(value1).equals(encodeString(value2)));
    }

    private String getStringFromBytes(byte[] hashedBytes) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< hashedBytes.length ;i++)
        {
            sb.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return  sb.toString();
    }

    private byte[] getSaltFromInputString(String input) {
        int length = input.length();
        String generatedSalt = "";
        if (length % 2 == 0) {
            generatedSalt = length + salt + salt.length();
        } else {
            generatedSalt = salt.length() + salt + length;
        }

        return generatedSalt.getBytes(StandardCharsets.UTF_8);
    }

}
