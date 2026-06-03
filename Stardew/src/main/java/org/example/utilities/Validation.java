package org.example.utilities;

import org.intellij.lang.annotations.Language;

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
}