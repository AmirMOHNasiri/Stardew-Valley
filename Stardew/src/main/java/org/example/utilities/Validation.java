package org.example.utilities;

import org.intellij.lang.annotations.Language;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean validateUsername(String username) {
        @Language("Regexp")
        String regex = "^[a-zA-Z\\d-]+$";
        return username.matches(regex);
    }

    public static boolean validatePasswordFormat(String password) {
        @Language("Regexp")
        String regex = "[a-zA-Z\\d?><,\"';:\\\\/|\\]\\[}{+=)(*@&^%$#!]+";
        return password.matches(regex);
    }

    public static String validatePasswordSecurity(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters";
        }
        Matcher matcher = Pattern.compile("[a-z]").matcher(password);
        if (!matcher.find()) {
            return "Password must contain a lowercase letter";
        }
        matcher = Pattern.compile("[A-Z]").matcher(password);
        if (!matcher.find()) {
            return "Password must contain a uppercase letter";
        }
        matcher = Pattern.compile("[0-9]").matcher(password);
        if (!matcher.find()) {
            return "Password must contain a number";
        }
        matcher = Pattern.compile("[?><,\"';:\\\\/|\\]\\[}{+=)(*@&^%$#!]+").matcher(password);
        if (!matcher.find()) {
            return "Password must contain a special character";
        }
        return "Success";
    }

    public static String createRandomPassword() {
        int length = (int) (Math.random() * 15 + 8);
        StringBuilder password = new StringBuilder();
        password.append((char) ((int) (Math.random() * 26) + 'A'));
        password.append((char) ((int) (Math.random() * 26) + 'a'));
        password.append((char) ((int) (Math.random() * 10) + '0'));
        password.append((char) ((int) (Math.random() * 14) + '!'));

        for (int i = 4; i < length; i++) {
            password.append((char) ((int) (Math.random() * 93) + '!'));
        }
        return password.toString();
    }

    public static boolean validateEmail(String email) {
        @Language("Regexp")
        String regex = "^(?<username>.+?)@(?<domain>.+?)\\.(?<tail>.+?)$";
        Matcher matcher = Pattern.compile(regex).matcher(email);
        if (!matcher.find()) {
            return false;
        }
        String username = matcher.group("username");
        String domain = matcher.group("domain");
        String tail = matcher.group("tail");
        if (thisCharCount('@', email) != 1) {
            return false;
        }
        if (!username.matches("^[a-zA-Z\\d_.-]+$")) {
            return false;
        }
        if(email.contains("..")) {
            return false;
        }
        if (invalidFrontAndEndChars(username)) {
            return false;
        }
        if (!domain.matches("[a-zA-Z\\d-]+")) {
            return false;
        }
        if (!tail.matches("[a-zA-Z]{2,}")) {
            return false;
        }
        if (invalidFrontAndEndChars(domain)) {
            return false;
        }
        if (invalidFrontAndEndChars(tail)) {
            return false;
        }
        return true;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static int thisCharCount(char ch, String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (ch == str.charAt(i)) {
                count ++;
            }
        }
        return count;
    }

    private static boolean invalidFrontAndEndChars(String string) {
        return !string.substring(0, 1).matches("[a-zA-Z\\d]")
                || !string.substring(string.length() - 1).matches("[a-zA-Z\\d]");
    }
}