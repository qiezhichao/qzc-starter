package com.qzc.util;

/**
 * 校验工具类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/13 20:28
 */
public class BaseValidatorUtil {

    /**
     * 校验手机号码
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/13 20:32
     */
    public static boolean validatePhoneNum(String phoneNum) {
        String patternStr = "^[1][3,4,5,7,8][0-9]{9}$";

        return BasePatternUtil.isMatch(patternStr, phoneNum);
    }

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
