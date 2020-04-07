package com.wxgzh.wxgzh.common;


import com.wxgzh.wxgzh.bean.WeiXinContext;

/**
 * TODO：微信常量：一般大写的都是常量
 * 以下常量都是从微信公共平台获取
 *
 * @author zhangjunchao
 * @date 2020/3/13
 */
@SuppressWarnings("WeakerAccess")
public class WeiXinUrl {

    private static final String APPID = "wx93c3573d2fcf4eec";

    private static final String APPSECRET = "7832ced77ff8df123354cf17878e7fcf";

    /**
     * 路径只替换 ACCESS_TOKEN，可用此方法
     * @param url
     * @return
     */
    public static String replaceAccessTokenUrl(String url){
        return url.replace("ACCESS_TOKEN", WeiXinContext.getAccessToken());
    }


    /**
     * 向微信获取accessToken的路径,get请求
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq" + ".com/cgi-bin/token?grant_type" +
            "=client_credential&appid=" + APPID + "&secret=" + APPSECRET;

    /**
     * 向微信申请菜单路径，post请求
     * https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN 调用此方法，要拼接ACCESS_TOKEN
     */

    public static final String MENU_ADD = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**
     * 上传微信素材（图片）路径 post请求
     *
     * 调用此路径，需要替换ACCESS_TOKEN 和 TYPE
     */
    public static final String POST_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * 获取微信media get请求：根据access_token 和 media_id
     *
     * 调用此路径，需要替换 ACCESS_TOKEN 和 MEDIA_ID
     */
    public static final String GET_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    /**
     * 发送微信模板消息，post请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 创建标签：一个公众号，最多可以创建100个标签。 post请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String ADD_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";

    /**
     * 获取公众号已创建的标签  get请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String QUERY_ALL_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";

    /**
     * 编辑标签   post请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String UPDATE_TAGS_NAME = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";


    /**
     * 删除标签   post请求
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，
     * 直到粉丝数不超过10w后，才可直接删除该标签。
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String DELETE_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";

    /**
     * 获取标签下粉丝列表 get请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String QUERY_TAGS_USERS = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";

    /**
     * 批量为用户打标签 post请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String ADD_USERS_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";

    /**
     * 批量为用户取消标签 post请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static final String DELETE_USERS_TAGS ="https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";

    /**
     * 获取用户身上的标签列表 post请求
     * 调用此路径，需要替换 ACCESS_TOKEN
     */
    public static String QUERY_USER_TAGS ="https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";







}



























