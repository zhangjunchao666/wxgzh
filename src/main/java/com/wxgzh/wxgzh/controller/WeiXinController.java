package com.wxgzh.wxgzh.controller;


import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.wxgzh.wxgzh.weixinmsg.MsgUtil;
import com.wxgzh.wxgzh.bean.*;
import com.wxgzh.wxgzh.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.internal.util.xml.XMLStreamException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * TODO：微信登录验证配置
 *
 * @author zhangjunchao
 * @date 2020/3/13
 */
@Api(tags = "1-微信公众号")
@ApiSort(1)
@Controller
public class WeiXinController {

    /**
     * 此token是在微信公共平台<接口配置信息>中的token
     */
    private static final String TOKEN = "weixintoken";


    /**
     * GET请求携带参数如下所示
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("微信公众号初始化")
    @GetMapping("/wget")
    public void init(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*
         * signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
         * timestamp 时间戳
         *  nonce 随机数
         *  echostr 随机字符串
         */

        // 获取微信的验证参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        // 1）将token、timestamp、nonce三个参数进行字典序排序
        String[] arrs = {WeiXinController.TOKEN,nonce,timestamp};
        Arrays.sort(arrs);

        // 2）将三个参数字符串拼接成一个字符串进行sha1加密(定义一个加密工具类 SecurityKit)
        StringBuilder sb = new StringBuilder();
        for (String a : arrs) {
            sb.append(a);
        }
        String sha1 = SecurityKit.sha1(sb.toString());

        // 3）开发者获得加密后的字符串可与signature对比，相等的话标识该请求来源于微信(Objects.requireNonNull(sha1)是防止空指针方法)
        System.out.println(Objects.requireNonNull(sha1).equals(signature));
        if (sha1.equals(signature)) {
            response.getWriter().println(echostr);
        }

        //if (sha1.equals(signature)) {
        //    return true;
        //} else {
        //    return false;
        //}
    }

    /**
     * 专门写一个类，接收微信的消息，所有的post请求都过来
     * 微信推给我们是一个 xml ，通过MsgUtil类处理成map
     * @param request
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("微信公众号post路径请求")
    @ResponseBody
    @PostMapping("/wget")
    public void getWeiXinInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, XMLStreamException {


        Map<String, String> msgMap = MsgUtil.requestMsgToMap(request);
        System.out.println(msgMap);

        // 获取消息类型
        String respCon = MsgUtil.handlerMsg(Objects.requireNonNull(msgMap));
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 把对应的消息返回去
        if (respCon != null) {
            response.getWriter().write(respCon);
        } else if ("unsubscribe".equals(msgMap.get("Event"))){
            System.out.println(msgMap.get("ToUserName")+"取消订阅");
        } else if ("subscribe".equals(msgMap.get("Event"))) {
            System.out.println(msgMap.get("ToUserName")+"关注公众号");
        }

    }


    /**
     * 微信的access_token每隔7200秒失效，写一个定时任务，每隔7000秒刷新access_token
     */
    @Scheduled(fixedDelay = 7000*1000)
    public void updateAccessToken(){

        // 发送http请求给微信，获取返回的access_token 和 失效时间 expires_in
        String doGet = HttpClientUtil.doGet(WeiXinUrl.ACCESS_TOKEN_URL);

        // 此处有可能出现转换异常,定义一个异常类，然后以日志的方式输出
        try {
            // 将返回的KV形式的数据，转换成自定义的AccessToken类
            AccessToken at = JSON.parseObject(doGet, AccessToken.class);
            // accessTokenoken
            String accessToken = Objects.requireNonNull(at).getAccess_token();
            System.out.println("accessToken："+accessToken);
            // 把accessTokeno写入到此类中，下面有测试方法 /getAccessToken
            WeiXinContext.setAccessToken(accessToken);
        } catch (Exception e) {
            WeiXinException err = JSON.parseObject(doGet, WeiXinException.class);
            System.err.println("获取accessTokeno异常："+ Objects.requireNonNull(err).getErrcode()+","+err.getErrmsg());
            // 发送异常后，自旋
            updateAccessToken();
        }
    }

    /**
     * 测试获取accessToken
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("获取accessToken")
    @ResponseBody
    @GetMapping("/testGetAccessToken")
    public String testGetAccessToken() {
        return WeiXinContext.getAccessToken();
    }

    /**
     * 设置微信公众号菜单
     * @return
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("设置微信公众号菜单")
    @ResponseBody
    @PostMapping("/WeiXinMenu")
    public Map<String,Object> testMenu() {
        HashMap<String, Object> map = new HashMap<>(16);


        // 设置一级菜单
        List<WeiXinMenu> wms = new ArrayList<>();
        // 设置平行一级菜单框
        WeiXinMenu wm1 = new WeiXinMenu();
        wm1.setId(1);
        wm1.setName("公司网站");
        wm1.setType("view");
        wm1.setUrl("https://www.hndfsj.com");
        // 增加一个一级菜单框
        wms.add(wm1);

        // 设置平行一级菜单框
        WeiXinMenu wm2 = new WeiXinMenu();
        wm2.setName("测试资源");
        ArrayList<WeiXinMenu> wm2Sub = new ArrayList<>();

        // 设置<测试资源>的二级菜单
        wm1 = new WeiXinMenu();
        wm1.setId(2);
        wm1.setName("事件测试");
        wm1.setType("click");
        wm1.setKey("A0001");
        wm2Sub.add(wm1);
        // 设置<测试资源>的二级菜单
        wm1 = new WeiXinMenu();
        wm1.setId(2);
        wm1.setName("扫描测试");
        wm1.setType("scancode_waitmsg");
        wm1.setKey("A0002");
        wm2Sub.add(wm1);
        wm2.setSub_button(wm2Sub);
        // 增加一个一级菜单框
        wms.add(wm2);

        // 按照微信要求，增加一个button
        HashMap<String, List<WeiXinMenu>> maps = new HashMap<>(16);
        maps.put("button", wms);
        System.out.println(JSON.toJSONString(maps));

        // 微信菜单数据
        String json = JSON.toJSONString(maps);

        // 设置请求微信的路径 url+accessToken
        String accessToken = WeiXinContext.getAccessToken();
        String url = WeiXinUrl.MENU_ADD + accessToken;

        //post方式请求
        String weiXinMsg = HttpClientUtil.doPost(url, json);
        System.out.println(weiXinMsg);



        map.put("dataJson", json);
        map.put("weiXinMsg", weiXinMsg);
        return map;

    }


    /**
     * 上传图片到微信，返回mediaId
     * @return
     * @throws IOException
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("上传图片到微信")
    @ResponseBody
    @GetMapping("/testMedia")
    public String testMedia() throws IOException {
        return MediaUtil.postMedia("d:/good.jpg", "image");

    }

    /**
     * 测试Media，上传素材（img）
     * @return
     * @throws IOException
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("上传素材")
    @ResponseBody
    @GetMapping("/testGetMedia")
    public void getMedia() throws IOException {
        String mediaId = MediaUtil.postMedia("d:/02.jpg", "image");
        String s = System.currentTimeMillis()+"";

        MediaUtil.getMedia(mediaId,new File("e:/"+s+".jpg"));
    }

    /**
     * 发送模板消息
     */
    @ApiOperationSupport(author = "zhangjunchao")
    @ApiOperation("发送模板消息")
    @ResponseBody
    @PostMapping("/templateMsg")
    public Object templateMsg(){
        TemplateMsg tm = new TemplateMsg();

        // 指定发送人，此人信息可从数据库查询或者session获取当前用户等等
        tm.setTouser("o8G4rxLhlLgYaGDt_LdQct7Z-0ZA");
        // 模板id
        tm.setTemplate_id(TemplateMsgEnum.TEST_TEMPLATEMSGID);
        // 模板的颜色
        tm.setTopcolor("#ff0000");
        tm.setUrl("http://www.baidu.com");
        HashMap<String, Object> data = new HashMap<>(16);
        data.put("num",new ModelMsgData("123","#00ff00"));
        tm.setData(data);
        System.out.println(JSON.toJSONString(tm));

        // 调用发送方法，并且返回状态码
        return MsgUtil.postTemplateMsg(tm);

    }


















}
