package com.prueba.usuarios.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtil {
    private static final String REGEX_PASSWORD = "^(?=.*[0-9]{2})(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

    public static boolean validarPassword(String password) {
        Pattern regex = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = regex.matcher(password);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
}
