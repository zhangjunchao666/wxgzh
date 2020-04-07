package com.wxgzh.wxgzh.common;

/**
 * TODO：微信http工具类
 *
 * @author zhangjunchao
 * @date 2020/3/24
 */
public class WeiXinUtil {

    public static String postReq(String url, String data, String type) {

        return HttpClientUtil.doPost(url, data, type);

    }

    public static String getReq(String url) {

        return HttpClientUtil.doGet(url);
    }




}
