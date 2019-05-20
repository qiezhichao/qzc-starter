package com.qzc.util;

/**
 * 校验工具类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/13 20:28
 */
public class BaseValidatorUtil {

    /**
     * 校验是否为数字
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/14 14:11
     */
    public static boolean validateIsDigit(String str) {
        String patternStr = "^[0-9]+$";

        return BasePatternUtil.isMatch(patternStr, str);
    }
}
