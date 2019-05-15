package com.qzc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/14 14:06
 */
public class BasePatternUtil {

    /**
     * 校验正则表达式和值是否匹配
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/14 14:08
     */
    public static boolean isMatch(String patternStr, String value) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
