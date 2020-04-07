package com.wxgzh.wxgzh.bean;

import java.io.Serializable;

/**
 * TODO：定义一个bean，来获取微信的access_token、expires_in
 *
 * @author zhangjunchao
 * @date 2020/3/13
 */
public class AccessToken implements Serializable {

    private String access_token;
    private String expires_in;
    private static String accessToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        AccessToken.accessToken = accessToken;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
