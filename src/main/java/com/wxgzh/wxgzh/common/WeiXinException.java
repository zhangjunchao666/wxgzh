package com.wxgzh.wxgzh.common;

/**
 * TODO：自定义微信异常类
 *
 * @author zhangjunchao
 * @date 2020/3/16
 */
public class WeiXinException {

    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
