package com.landasoft.taoj.common.pojo;

/**
 * Layui im 发送消息响应类型
 * @author zhaoyuan
 * @date 2020,July 15
 */
public class LayuiIMSendMessageResult {

    //随便定义，用于在服务端区分消息类型
    private String type;
    private LayuiIMSendData data;

    public LayuiIMSendMessageResult() {
    }

    public LayuiIMSendMessageResult(String type, LayuiIMSendData data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LayuiIMSendData getData() {
        return data;
    }

    public void setData(LayuiIMSendData data) {
        this.data = data;
    }
}
