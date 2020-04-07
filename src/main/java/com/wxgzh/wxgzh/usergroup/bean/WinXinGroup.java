package com.wxgzh.wxgzh.usergroup.bean;

import java.io.Serializable;

/**
 * TODO：微信分组
 *
 * @author zhangjunchao
 * @date 2020/3/24
 */
public class WinXinGroup implements Serializable {

    private int id;
    private String name;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
