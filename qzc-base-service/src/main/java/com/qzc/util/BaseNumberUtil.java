package com.qzc.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 数字工具类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/11 16:07
 */
public class BaseNumberUtil {

    private static final String UNIT_100000000 = "亿";
    private static final String UNIT_10000 = "万";

    /**
     * 将数字转换成带单位的字符串
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/11 16:08
     */
    public static String convertNum2Str(long number) {
        if (number > 100000000) {
            double value = number / 100000000.0;

            return String.format("%.2f", value) + UNIT_100000000;
        } else if (number > 10000) {
            double value = number / 10000.0;

            return String.format("%.2f", value) + UNIT_10000;
        } else {
            return String.valueOf(number);
        }
    }

    /**
     * 取小数点后n位
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/11 15:21
     */
    public static double trunkDouble(double d, int n) {
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 取小数点后n位
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/11 15:21
     */
    public static double trunkDouble(String d, int n) {
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 生成随机6位数字字符串值
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 13:04
     */
    public static String genter6RandomNumStr() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

}
