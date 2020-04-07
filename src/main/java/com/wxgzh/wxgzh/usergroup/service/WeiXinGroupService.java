package com.wxgzh.wxgzh.usergroup.service;

import com.wxgzh.wxgzh.usergroup.bean.WinXinGroup;

import java.util.List;

/**
 * TODO：微信用户分组管理接口
 *
 * @author zhangjunchao
 * @date 2020/3/24
 */
public interface WeiXinGroupService {


    /**
     * 添加微信分组用户
     * @param weiXinGroup
     * @return
     */
    String add(WinXinGroup weiXinGroup);

    /**
     * 查询所有微信分组用户
     * @return
     */
    List<WinXinGroup> queryAll();

    /**
     * 根据openid查询用户所在分组
     * @param openid
     * @return
     */
    WinXinGroup queryUserGroup(String openid);

    /**
     * 修改分组名称
     * @param groupid
     * @param groupName
     */
    void updateGroupName(int groupid, String groupName);

    /**
     * 移动用户分组
     * @param openid
     * @param groupId
     */
    void moveUserToGroup(String openid, int groupId);

    /**
     * 批量移动用户分组
     * @param openids
     * @param groupid
     */
    void moveUsersToGroup(List<String> openids, int groupid);

    /**
     * 删除分组
     * @param groupid
     */
    void deleteGroup(int groupid);
























}
