package com.landasoft.taoj.mypojo;

/**
 * 前台用户实体
 * @author zhaoyuan
 * @date 2020,June 17
 */
public class IndexUser {
    private String username;
    private String id;
    private String sign;
    private String avatar;
    private String alias;

    public IndexUser() {
    }

    public IndexUser(String username, String id, String sign, String avatar,String alias) {
        this.username = username;
        this.id = id;
        this.sign = sign;
        this.avatar = avatar;
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
