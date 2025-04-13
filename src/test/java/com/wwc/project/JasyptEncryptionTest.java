package com.wwc.project;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.jasypt.encryption.StringEncryptor;

@SpringBootTest
@EnableEncryptableProperties
public class JasyptEncryptionTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void run() throws Exception {

        // 原始明文密碼
        String originalPassword = "testPassWord";

        // 加密
        String encryptedPassword = encrypt(originalPassword);

        // 列印加密前後的結果對比
        System.out.println("原始明文密碼為：" + originalPassword);
        System.out.println("加密後的結果為：" + encryptedPassword);

        // 解密
        String decryptedPassword = decrypt(encryptedPassword);
        System.out.println("解密後的結果為：" + decryptedPassword);
    }

    private String encrypt(String originPassword) {
        return encryptor.encrypt(originPassword);
    }

    private String decrypt(String encryptedPassword) {
        return encryptor.decrypt(encryptedPassword);
    }
}