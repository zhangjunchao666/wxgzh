package com.wxgzh.wxgzh.bean;

import java.io.Serializable;

/**
 * TODO：
 *
 * @author zhangjunchao
 * @date 2020/3/23
 */
public class ModelMsgData implements Serializable {

    private String value;
    private String color;

    public ModelMsgData(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
