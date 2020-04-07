package com.wxgzh.wxgzh.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO：加密工具类
 *
 * @author zhangjunchao
 * @date 2020/3/13
 */
public class SecurityKit {

    /**
     * 16进制
     * @param str
     * @return
     */
    public static String sha1(String str) {
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] msg = md.digest();

            for (byte b : msg) {
                sb.append(String.format("%02x",b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
