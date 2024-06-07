package org.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressUtils {

    private static final Pattern pat = Pattern.compile(
            "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");

    /**
     * <p>
     * 私有化构造方法
     * </p>
     */
    private IpAddressUtils() {
    }

    /**
     * <p>
     * 判断字符串是否为IP地址
     * </p>
     *
     * @param str 字符串
     * @return 是否为IP地址
     */
    public static boolean isIpAddress(String str) {

        if (StringUtils.isBlank(str) || str.length() < 7 || str.length() > 15) {
            return false;
        }

        Matcher mat = pat.matcher(str);
        return mat.find();
    }

}

