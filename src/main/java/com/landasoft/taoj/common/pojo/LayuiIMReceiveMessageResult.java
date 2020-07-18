package com.landasoft.taoj.common.pojo;

import com.alibaba.fastjson.JSONObject;

/**
 * Layui im 接收消息响应类型
 * @author zhaoyuan
 * @date 2020,July 15
 */
public class LayuiIMReceiveMessageResult {
    //消息类型 0:系统消息 1：自动回复消息 2：人工消息
    private Integer msgType;

    //消息内容
    private JSONObject msgData;

    public LayuiIMReceiveMessageResult() {
    }

    public LayuiIMReceiveMessageResult(Integer msgType, JSONObject msgData) {
        this.msgType = msgType;
        this.msgData = msgData;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public JSONObject getMsgData() {
        return msgData;
    }

    public void setMsgData(JSONObject msgData) {
        this.msgData = msgData;
    }
}
