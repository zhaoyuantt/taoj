package com.landasoft.taoj.common.pojo;

import java.util.List;

/**
 * Lay ui IM 好友列表响应类型
 * @author zhaoyuan
 * @date 2020,July 17
 */

/**
 * "friend": [{
 *       "groupname": "前端码屌" //好友分组名
 *       ,"id": 1 //分组ID
 *       ,"list": [{ //分组下的好友列表
 *         "username": "贤心" //好友昵称
 *         ,"id": "100001" //好友ID
 *         ,"avatar": "a.jpg" //好友头像
 *         ,"sign": "这些都是测试数据，实际使用请严格按照该格式返回" //好友签名
 *         ,"status": "online" //若值为offline代表离线，online或者不填为在线
 *       }, …… ]
 *     }, …… ]
 */
public class LayuiIMFriendResult {

    private String groupname;
    private String id;
    private List<LayuiIMMainPanelIndividual> list;

    public LayuiIMFriendResult() {
    }

    public LayuiIMFriendResult(String groupname, String id, List<LayuiIMMainPanelIndividual> list) {
        this.groupname = groupname;
        this.id = id;
        this.list = list;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LayuiIMMainPanelIndividual> getList() {
        return list;
    }

    public void setList(List<LayuiIMMainPanelIndividual> list) {
        this.list = list;
    }
}
