package com.landasoft.taoj.common.pojo;

/**
 * Layui im 主面板结构类型
 */
public class LayuiIMInitResult {

    //0表示成功，其它表示失败
    private String code;
    //失败信息
    private String msg;

    private LayuiIMMainPanelDataResult data;

    public LayuiIMInitResult() {
    }

    public LayuiIMInitResult(String code, String msg, LayuiIMMainPanelDataResult data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LayuiIMMainPanelDataResult getData() {
        return data;
    }

    public void setData(LayuiIMMainPanelDataResult data) {
        this.data = data;
    }
}
