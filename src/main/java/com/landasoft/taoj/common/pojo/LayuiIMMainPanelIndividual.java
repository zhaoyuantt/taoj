package com.landasoft.taoj.common.pojo;

/**
 * Layui IM 主面板我的信息
 */
public class LayuiIMMainPanelIndividual {
    /**
     * "username": "纸飞机"
     *       ,"id": "100000"
     *       ,"status": "online"
     *       ,"sign": "在深邃的编码世界，做一枚轻盈的纸飞机"
     *       ,"avatar": "http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg"
     */

    private String username;
    private String id;
    private String status;
    private String sign;
    private String avatar;

    public LayuiIMMainPanelIndividual() {
    }

    public LayuiIMMainPanelIndividual(String username, String id, String status, String sign, String avatar) {
        this.username = username;
        this.id = id;
        this.status = status;
        this.sign = sign;
        this.avatar = avatar;
    }

    public LayuiIMMainPanelIndividual(String username, String id, String sign, String avatar) {
        this.username = username;
        this.id = id;
        this.sign = sign;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
