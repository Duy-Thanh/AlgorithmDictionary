package com.algorithmswiki.apps.backend.backend;

import java.util.regex.*;

public class EmailValidator {
    private static final String regex = "^(.+)@(.+)$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        String valid = (matcher.matches() ? "valid" : "invalid");

        return (valid == "valid");
    }
}