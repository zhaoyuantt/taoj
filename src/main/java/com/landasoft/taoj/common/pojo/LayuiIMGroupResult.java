package com.landasoft.taoj.common.pojo;

/**
 * Layui IM 群响应类型
 * @author zhaoyuan
 * @date 2020,July 17
 */

/**
 * {
 *       "groupname": "前端群" //群组名
 *       ,"id": "101" //群组ID
 *       ,"avatar": "a.jpg" //群组头像
 *     }
 */
public class LayuiIMGroupResult {

    private String groupname;
    private String id;
    private String avatar;

    public LayuiIMGroupResult() {
    }

    public LayuiIMGroupResult(String groupname, String id, String avatar) {
        this.groupname = groupname;
        this.id = id;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
