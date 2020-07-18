package com.landasoft.taoj.common.pojo;

/**
 * Layui im 发送消息左边响应类型
 * @author zhaoyuan
 * @date 2020,July 15
 */
public class To {

    private String avatar;
    private String id;
    //其实是系统名，目前为固定值，taoj
    private String name;
    //目前为固定值，friend
    private String type;

    private String username;

    private String status;

    private String sign;

    private String historyTime;

    public To() {
    }

    public To(String avatar, String id, String name, String type) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }
}
