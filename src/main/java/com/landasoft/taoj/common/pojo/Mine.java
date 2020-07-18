package com.landasoft.taoj.common.pojo;

/**
 * Layui im 发送消息右边响应信息
 * @author zhaoyuan
 * @date 2020,July 15
 */
public class Mine {

    //头像
    private String avatar;
    //消息内容，用户发送
    private String content;
    //用户Id
    private String id;
    //???
    private boolean mine;
    //用户名
    private String username;

    public Mine() {
    }

    public Mine(String avatar, String content, String id, boolean mine, String username) {
        this.avatar = avatar;
        this.content = content;
        this.id = id;
        this.mine = mine;
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
