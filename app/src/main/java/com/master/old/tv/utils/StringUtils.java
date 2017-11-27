package com.master.old.tv.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.Random;

/**
 * Create by MasterMan
 * Description:
 *   add --> string 工具类
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: ２０１７年１１月２７
 */
public class StringUtils {
    /**
     * 去掉特殊字符
     *
     * @param s
     * @return
     */
    public static String removeOtherCode(String s) {
        if (TextUtils.isEmpty(s))
            return "";
        s = s.replaceAll("\\<.*?>|\\n", "");
        return s;
    }

    /**
     * 判断非空
     *
     * @param str
     * @return
     */
    public static String isEmpty(String str) {
        String result = TextUtils.isEmpty(str) ? "" : str;
        return result;
    }

    /**
     * 根据Url获取catalogId
     *
     * @param url
     * @return
     */
    public static String getCatalogId(String url) {
        String catalogId = "";
        String key = "catalogId=";
        if (!TextUtils.isEmpty(url) && url.contains("="))
            catalogId = url.substring(url.indexOf(key) + key.length(), url.lastIndexOf("&"));
        return catalogId;
    }

    /**
     * 获取区间内的随机数
     * ＠param min
     * @param  max
     * */
    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    public static String getErrorMsg(String msg) {
        if (msg.contains("*")) {
            msg = msg.substring(msg.indexOf("*") + 1);
            return msg;
        } else
            return "";
    }

    public static void setIconDrawable(Context mContext, TextView view, IIcon icon, int size, int padding) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(mContext)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(size),
                null, null, null);
        view.setCompoundDrawablePadding(ScreenUtil.dip2px(mContext, padding));
    }

    /**
     * 如果字符串没有超过最长显示长度返回原字符串，　否则从开头截取指定长度...
     * @param str
     *
     * @param length
     *
     * @return
     *
     * */
    public static String trimString(String str, int length) {
        if (str ==null) {
            return "";
        } else if (str.length() > length) {
            return str.substring(0, length - 3) + "...";
        } else {
            return str;
        }
    }

    /**
     * 功能：检查这个字符串是不是空字符串。<br/>
     * 如果这个字符串为null或者trim后为空字符串则返回true，否则返回false。
     *
     * @return boolean
     */
    public static boolean isStrEmpty(String chkStr) {
        if (chkStr == null) {
            return true;
        } else {
            return "".equals(chkStr.trim()) ? true : false;
        }
    }

    /**
     * StringUtil.reverse(null)  = null
     * StringUtil.reverse("")    = ""
     * StringUtil.reverse("bat") = "tab"
     * </pre>
     *
     * @param str
     *            源字符串
     * @return String
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }
}
