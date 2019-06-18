package com.ayman.MessagesSystem.Code.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean validation(String... temp) { //if at least 1 textfield is empty it will return true
        for (String s : temp) {
            if (s.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public static boolean validationForPass(String temp) { //if at least 1 textfield is empty it will return true
        if(temp.length() < 5){
            return true;
        } else {
            return false;
        }
    }

    public static boolean containsInt(String... temp) {
        Pattern p = Pattern.compile(".*\\d+.*");
        Pattern p2 = Pattern.compile("[^a-z0-9 ]");
        for (String s : temp) {
            Matcher m = p.matcher(s);
            Matcher m2 = p2.matcher(s);
            boolean b = m.find();
            boolean b2 = m2.find();

            if (b || b2) {
                return true;//True : Has something   //false: "Clean"
            }
        }
        return false;
    }
}
