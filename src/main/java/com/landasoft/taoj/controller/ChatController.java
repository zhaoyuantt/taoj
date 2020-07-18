package com.landasoft.taoj.controller;

import com.landasoft.taoj.common.pojo.LayuiIMInitResult;
import com.landasoft.taoj.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 在线客服Controller
 * @author zhaoyuan
 * @date 2020,July 17
 */
@Controller
@RequestMapping("/admin/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping("/init/{adminUserName}")
    @ResponseBody
    public LayuiIMInitResult getLayuiIMInitResult(@PathVariable String adminUserName){
        LayuiIMInitResult layuiIMInitResult = chatService.getLayuiIMInitData(adminUserName);
        return layuiIMInitResult;
    }
}
