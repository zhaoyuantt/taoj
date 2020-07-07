package com.landasoft.taoj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转Controller
 * @author zhaoyuan
 * @date 2020,July 7 3:25 pm
 */
@Controller
@RequestMapping("/page")
public class PageController {

    /**
     * 跳转到后台管理页面首页
     * @return
     */
    @RequestMapping("/admin/index")
    public ModelAndView showAdminIndexPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }

    /**
     * 跳转到问题管理页面
     * @return
     */
    @RequestMapping("/admin/question")
    public ModelAndView showQuestionInfiPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/question_info");
        return modelAndView;
    }

    /**
     * 跳转到问题新增页面
     * @return
     */
    @RequestMapping("/admin/questionA")
    public ModelAndView showAddQuestionPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/question_add");
        return modelAndView;
    }
}
