package com.qzc;


import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void dbInfoEncryption() {

        //加密url
        String url = stringEncryptor.encrypt("jdbc:mysql://localhost:3306/xdd?useUnicode=true&characterEncoding=utf8");
        System.out.println(url);

        //加密user
        String user = stringEncryptor.encrypt("root");
        System.out.println(user);

        //加密passwd
        String pwd = stringEncryptor.encrypt("1234");
        System.out.println(pwd);
    }
}
