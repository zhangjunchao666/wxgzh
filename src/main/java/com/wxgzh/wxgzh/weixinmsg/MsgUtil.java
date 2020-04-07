package com.wxgzh.wxgzh.weixinmsg;

import com.alibaba.fastjson.JSON;
import com.wxgzh.wxgzh.bean.TemplateMsg;
import com.wxgzh.wxgzh.bean.WeiXinContext;
import com.wxgzh.wxgzh.common.HttpClientUtil;
import com.wxgzh.wxgzh.common.WeiXinMsgType;
import com.wxgzh.wxgzh.common.WeiXinUrl;
import jdk.internal.util.xml.XMLStreamException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO：处理微信推送过来的  xml 消息，利用dom4j的Document方法转换成map
 *
 * @author zhangjunchao
 * @date 2020/3/17
 */
public class MsgUtil {

    /**
     * 回复的文本消息
     */
    private static Map<String, String> replyMsgs = new HashMap<>(16);
    // 定义一个静态方法，回复消息，用户在微信输入的消息，必须包含map的key才有正确回复，比如：123 hello run
    static {
        replyMsgs.put("1", "公司官网：https://www.hndfsj.com");
        replyMsgs.put("2", "招贤纳士：java、前端、产品、硬件工程师，联系电话：10086，emai：10086@hndfsj.com");
        replyMsgs.put("3", "精诚合作：高速运维、软件、硬件等");
        replyMsgs.put("4", "人工服务：10086");
    }
    /**
     * TODO: 判断消息类型,根据类型进行对应的处理
     * @param msgMap
     * @return
     */
    public static String handlerMsg(Map<String, String> msgMap) throws IOException, XMLStreamException {

        // 获取消息类型
        String msgType = msgMap.get("MsgType");


        // 判断消息类型进行处理
        if (msgType.equals(WeiXinMsgType.MSG_EVENT_TYPE)) {



           // 文本消息处理 客户端发送 1 2 3 4 返回自定义的 replyMsgs 中对应的消息内容
        } else if (msgType.equals(WeiXinMsgType.MSG_TEXT_TYPE)) {
            return textTypeHandler(msgMap);
            // 图片消息处理-逻辑：客户端发送一个图片，返回一个图片
        } else if (msgType.equals(WeiXinMsgType.MSG_IMAGE_TYPE)) {
            String mediaId = "tf9SEAPEK8KW62rIKZDmAZelV2W2yv0x-iCCBw614nnvh5XpyjNVQzFE9AMmG76L";
            return imageTypeHandler(msgMap,mediaId);
        }



        return null;
    }

    private static String imageTypeHandler(Map<String, String> msgMap, String mediaId) throws IOException,
            XMLStreamException {

        HashMap<String, String> imageMap = new HashMap<>(16);
        imageMap.put("ToUserName", msgMap.get("FromUserName"));
        imageMap.put("FromUserName", msgMap.get("ToUserName"));
        imageMap.put("CreateTime", System.currentTimeMillis()+"");
        imageMap.put("MsgType", "image");
        imageMap.put("Image", "<MediaId>"+mediaId+"</MediaId>");
        // 将map转换成xml格式的数据
        return mapToXml(imageMap);
    }


    private static String textTypeHandler(Map<String, String> msgMap) throws IOException, XMLStreamException {
        HashMap<String, String> textMap = new HashMap<>(16);
        textMap.put("ToUserName", msgMap.get("FromUserName"));
        textMap.put("FromUserName", msgMap.get("ToUserName"));
        textMap.put("CreateTime", System.currentTimeMillis()+"");
        textMap.put("MsgType", "text");

        String replyContent = "你请求的消息内容不正确！";
        String content = msgMap.get("Content");
        if (replyMsgs.containsKey(content)) {
            replyContent = replyMsgs.get(content);
        }
        textMap.put("Content", replyContent);

        // 将map转换成xml格式的数据
        return mapToXml(textMap);
    }

    /**
     * 将map转换成xml格式的数据(单位测试-testMapToXml())
     * @param map
     * @return
     */
    public static String mapToXml(HashMap<String, String> map) throws IOException, XMLStreamException {

        Document d = DocumentHelper.createDocument();
        Element root = d.addElement("xml");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            root.addElement(key).addText(map.get(key));
        }
        StringWriter sw = new StringWriter();

        // 防止转换，和数据库的CDATA类似
        XMLWriter xmlWriter = new XMLWriter(sw);
        xmlWriter.setEscapeText(false);
        xmlWriter.write(d);
        return sw.toString();
    }


    /**
     * 将微信推送的xml消息，转换成map
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> requestMsgToMap(HttpServletRequest request) throws IOException {

        HashMap<String, String> maps = new HashMap<>(16);

        // 获取string格式的xml
        String xml = requestToXml(request);
        try {
            // 转换成document对象
            Document document = DocumentHelper.parseText(xml);
            // 获取element对象
            Element root = document.getRootElement();
            // 获取所有元素
            List<Element> elements = root.elements();
            // 放入map中
            for (Element e : elements) {
                maps.put(e.getName(), e.getTextTrim());
            }
            return maps;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 string 格式的 xml
     * @param request
     * @return
     * @throws IOException
     */
    private static String requestToXml(HttpServletRequest request) throws IOException {
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 微信模板消息业务逻辑处理
     * @param templateMsg
     * @return
     */
    public static String postTemplateMsg(TemplateMsg templateMsg) {

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        // 微信模板消息的路径和模板json
        String url = WeiXinUrl.SEND_TEMPLATE_MSG.replace("ACCESS_TOKEN", WeiXinContext.getAccessToken());
        String json = JSON.toJSONString(templateMsg);
        // 调用方法，执行模板消息
        return HttpClientUtil.doPost(url, json,"application/json");
    }





}
