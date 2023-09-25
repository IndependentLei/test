package com.shebao.test.util;

import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class sm4Demo {

    //key必须是16字节，即128位
    final static String key = "4600000100000000";

    //指明加密算法和秘钥
    static SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());

    //加密为16进制，也可以加密成base64/字节数组
    public static String encryptSm4(String plaintext) {
        return sm4.encryptHex(plaintext);
    }

    //解密
    public static String decryptSm4(String ciphertext) {
        return sm4.decryptStr(ciphertext);
    }

    public static void main(String[] args) {
        String content = "hjikY0WSw6r6rHPS";
        String plain = encryptSm4(content).toUpperCase().substring(0,16);

        System.out.println(plain);
        String s = new SymmetricCrypto("SM4/ECB/PKCS5Padding", plain.getBytes()).encryptHex("{\"hnCode\":\"4601994269996\",\"smsCode\":\"123455\",\"appcode\":460000099}");
        System.out.println(s);
        String s1 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", plain.getBytes()).decryptStr("2190977128DF0BD10B0018C75C1959CBE819DDC1C35C1FE1A5FBF016F9FD05F196F3EF6E9A2A412018885ED2F7DF40FE");

        System.out.println(s1);
//        String cipher = decryptSm4(plain);
//        System.out.println(plain + "\n" + cipher);
    }
}
