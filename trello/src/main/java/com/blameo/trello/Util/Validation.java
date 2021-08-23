package com.blameo.trello.Util;

public class Validation {
    private static final String regex = "^[a-zA-Z]+[a-zA-Z0-9]*@{1}gmail.com+$";

    public static Boolean validateEmail(String email) {
        return email.matches(regex);
    }

    public static Boolean validatePassword(String pass) {
        return pass.length() >= 6;
    }
}
