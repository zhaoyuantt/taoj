package com.landasoft.taoj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试环境的搭建
 * @author zhaoyuan
 * @date 2020,July 7 9:57 pm
 */

@Controller
public class TestController {

    @RequestMapping("/webPtest")
    public void webPtest(){
        System.out.println("ok");
    }
}
