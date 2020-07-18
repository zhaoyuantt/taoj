package com.landasoft.taoj.service;

import com.landasoft.taoj.common.pojo.LayuiIMInitResult;

/**
 * 人工客服有关Service接口
 * @author zhaoyuan
 * @date 2020,July 17
 */
public interface ChatService {

    /**
     * 获得Layui IM 主面板数据
     * @param adminUserName
     * @return
     */
    LayuiIMInitResult getLayuiIMInitData(String adminUserName);
}
