package com.cj.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/23.
 */

public class MatchUtils {

    private static final String REGEX_MOBILE = "[1]\\d{10}";
    private final static Pattern phone = Pattern.compile(REGEX_MOBILE);

    public static boolean isMobilePhone(CharSequence phoneNum) {
        if (TextUtils.isEmpty(phoneNum))
            return false;
        return phone.matcher(phoneNum).matches();
    }
}
