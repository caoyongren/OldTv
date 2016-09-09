package com.zcy.ghost.ghost.utils;

import android.text.TextUtils;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class StringUtils {
    public static String removeOtherCode(String s) {
        if (TextUtils.isEmpty(s))
            return "";
        s = s.replaceAll("\\<.*?>|\\n", "");
        return s;
    }

    public static String isEmpty(String str) {
        String result = TextUtils.isEmpty(str) ? "" : str;
        return result;
    }

}
