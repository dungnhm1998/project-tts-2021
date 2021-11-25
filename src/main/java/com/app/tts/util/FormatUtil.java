package com.app.tts.util;

import com.app.tts.encode.Md5Code;
import org.apache.commons.validator.routines.EmailValidator;

public class FormatUtil {
    public static String getMd5(String input) {
        String output = Md5Code.md5(input);
        return output;
    }

    public static boolean validateEmail(String email) {
        Boolean isEmail = EmailValidator.getInstance().isValid(email);
        return isEmail;
    }
}
