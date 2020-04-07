package com.wxgzh.wxgzh.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * TODO：HttpClien工具类
 *
 * @author zhangjunchao
 * @date 2020/3/16
 */
@SuppressWarnings({"Duplicates", "WeakerAccess", "UnnecessaryReturnStatement"})
public class HttpClientUtil {

    /**
     * get请求
     *
     * @param url 请求的路径
     */
    public static String doGet(String url)  {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                httpclient.close();
                return result;
            }
            httpclient.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * post请求，此方法是请求微信的菜单等方法,下面还有一个重载方法，俩个都可以使用
     * @param url 请求的路径
     * @param json 请求的数据（json)
     */
    public static String doPost(String url,String json)  {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头，application/json说明传的是application中的json数据
        httpPost.addHeader("Content_Type","application/json");

        // 设置 entity
        StringEntity stringEntity = new StringEntity(json, ContentType.create("application/json","utf-8"));

        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null ;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 方法重载
     * @param url 请求的路径
     * @param json 请求的数据（json)
     * @param type 请求头数据类型
     */
    public static String doPost(String url, String json, String type)  {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头，application/json说明传的是application中的json数据
        httpPost.addHeader("Content_Type",type);

        // 设置 entity
        StringEntity stringEntity = new StringEntity(json, ContentType.create(type,"utf-8"));

        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null ;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 下载
     *
     * @param url 下载文件的路径
     * @param fileName  文件名
     */
    public static void download(String url, String fileName) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();

                byte[] bytes = EntityUtils.toByteArray(entity);
                File file = new File(fileName);
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(bytes);

                EntityUtils.consume(entity);

                httpclient.close();
                fout.flush();
                fout.close();
                return;
            }
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        return;
    }
}
