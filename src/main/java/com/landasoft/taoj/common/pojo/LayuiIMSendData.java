package com.landasoft.taoj.common.pojo;

/**
 * Layui im 发送消息 右边和左边消息集合类型
 * @author zhaoyuan
 * @date 2020,July 15
 */
public class LayuiIMSendData {

    private Mine mine;
    private To to;

    public LayuiIMSendData() {
    }

    public LayuiIMSendData(Mine mine, To to) {
        this.mine = mine;
        this.to = to;
    }

    public Mine getMine() {
        return mine;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }
}
