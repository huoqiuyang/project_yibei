package com.yibei.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtils {
    public static boolean isEmail(String email){
        String check = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhone(String phone){
        String check = "^1\\d{10}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phone);
        return matcher.matches();
    }
}
