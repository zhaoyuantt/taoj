package com.landasoft.taoj.controller;

import com.landasoft.taoj.service.QuestionService;
import com.landasoft.taoj.utils.MyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试环境的搭建
 * @author zhaoyuan
 * @date 2020,July 7 9:57 pm
 */

@Controller
public class TestController {

    @Autowired
    private QuestionService questionService;
    
    @RequestMapping("/webPtest")
    public void webPtest(){
        System.out.println("ok");
    }

    @RequestMapping("/testQuerySolr/{name}")
    @ResponseBody
    public MyResult testGetQuestionListWithSolrByName(@PathVariable String name){
        MyResult myResult = questionService.getQuestionListWithSolrByName(name);
        return myResult;
    } 
}
