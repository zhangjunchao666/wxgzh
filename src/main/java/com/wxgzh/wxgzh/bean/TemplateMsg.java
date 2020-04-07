package com.wxgzh.wxgzh.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * TODO：
 *
 * @author zhangjunchao
 * @date 2020/3/23
 */
public class TemplateMsg implements Serializable {

    private String touser;
    private String template_id;
    private String url;
    private String topcolor;
    private Map<String, Object> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
