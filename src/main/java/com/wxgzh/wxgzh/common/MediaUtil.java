package com.wxgzh.wxgzh.common;

import com.alibaba.fastjson.JSON;
import com.wxgzh.wxgzh.bean.WeiXinContext;
import com.wxgzh.wxgzh.bean.WeiXinMedia;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO：处理图片消息
 *
 * @author zhangjunchao
 * @date 2020/3/20
 */
public class MediaUtil {

    public static String postMedia(String path, String type) throws IOException {

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try {
            client = HttpClients.createDefault();
            String url = WeiXinUrl.POST_MEDIA;
            // 上传路径url
            String mediaUrl = url.replace("ACCESS_TOKEN", WeiXinContext.getAccessToken()).replace("TYPE", type);
            HttpPost post = new HttpPost(mediaUrl);
            FileBody fb = new FileBody(new File(path));
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("media",fb).build();
            post.setEntity(reqEntity);
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

                String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                WeiXinMedia weiXinMedia = JSON.parseObject(json, WeiXinMedia.class);
                // TODO:此处要把 MediaId 存到数据库
                return weiXinMedia.getMedia_id();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
            if (response != null) {
                response.close();
            }
        }

        return null;
    }

    /**
     * 根据access_token 和 media_id 获取Media
     * @param mediaId
     * @return
     * @throws IOException
     */
    public static void getMedia(String mediaId,File file) throws IOException {

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try {
            client = HttpClients.createDefault();
            String url = WeiXinUrl.GET_MEDIA;
            // 上传路径url
            String mediaUrl = url.replace("ACCESS_TOKEN", WeiXinContext.getAccessToken()).replace("MEDIA_ID", mediaId);
            HttpGet httpGet = new HttpGet(mediaUrl);
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                InputStream inputStream = response.getEntity().getContent();
                // 将文件保存下来
                FileUtils.copyInputStreamToFile(inputStream, file);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
            if (response != null) {
                response.close();
            }
        }
    }




}
