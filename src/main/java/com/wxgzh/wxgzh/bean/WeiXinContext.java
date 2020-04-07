package com.wxgzh.wxgzh.bean;

import java.io.Serializable;

/**
 * TODO：定义一个微信文本类，进行存储accessToken
 *
 * @author zhangjunchao
 * @date 2020/3/16
 */
public class WeiXinContext implements Serializable {


    private static String accessToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        WeiXinContext.accessToken = accessToken;
    }
}
