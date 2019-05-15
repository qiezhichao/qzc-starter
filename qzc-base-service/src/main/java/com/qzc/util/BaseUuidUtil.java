package com.qzc.util;

import java.util.UUID;

/**
 * UUID 工具类
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/11 15:58
 */
public class BaseUuidUtil {

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 生成UUID
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/11 15:57
     */
    public static String generateUuid(int uuidLength) {
        uuidLength = uuidLength > 10 ? 10 : uuidLength;

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < uuidLength; i++) {
            String str = uuid.substring(i * 3, i * 3 + 2);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();
    }

    /**
     * 生成10位UUID
     *
     * @Author: qiezhichao
     * @CreateDate: 2019/5/11 15:58
     */
    public static String generateUuid() {
        return generateUuid(10);
    }

}
