package com.landasoft.taoj.controller;

import com.landasoft.taoj.constant.IndexUserConstant;
import com.landasoft.taoj.pojo.TQuestionItem;
import com.landasoft.taoj.service.QuestionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 页面跳转Controller
 * @author zhaoyuan
 * @date 2020,July 7 3:25 pm
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private QuestionItemService questionItemService;
    @Value("${INDEX_SESSION_TIMEOUT}")
    private String INDEX_SESSION_TIMEOUT;

    /**
     * 跳转到后台管理页面首页
     * @param request
     * @return
     */
    @RequestMapping("/admin/index")
    public ModelAndView showAdminIndexPage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        //从session中获取用户信息
        Object userDetail = request.getSession().getAttribute("TUSERDETAIL");
        modelAndView.addObject("TUSERDETAIL",userDetail);
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

    /**
     * 跳转到问题明细页面
     * @param qid
     * @return
     */
    @RequestMapping("/admin/question/item/{qid}")
    public ModelAndView showQueationItemPage(@PathVariable String qid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("QID",qid);
        modelAndView.setViewName("admin/question_item_info");
        return modelAndView;
    }

    @RequestMapping("/admin/question/itemA/{qid}")
    public ModelAndView showAddQuestionItemPage(@PathVariable String qid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("QID",qid);
        modelAndView.setViewName("admin/question_item_add");
        return modelAndView;
    }

    /**
     * 跳转到问题编辑页面
     * @param id
     * @return
     */
    @RequestMapping("/admin/question/itemE/{id}")
    public ModelAndView showQuestionItemEditPage(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        TQuestionItem questionItem = questionItemService.getQuestionItemById(id);
        modelAndView.setViewName("admin/question_item_edit");
        modelAndView.addObject("QUESTIONITEM",questionItem);
        return modelAndView;
    }

    /**
     * 跳转到后台登录页面
     * @return
     */
    @RequestMapping("/admin/login")
    public ModelAndView showLoginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/login");
        return modelAndView;
    }

    /**
     * 跳转到门户用户聊天页面
     * @param indexUsername
     * @return
     */
    @RequestMapping("/index/chat/{indexUsername}")
    public ModelAndView showIndexChatPage(@PathVariable String indexUsername,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        //TODO 用户信息存入session，以后版本改为登录后储存
        HttpSession session = request.getSession();
        session.setAttribute("TUSERDETAIL",indexUsername);
        //设置过期时间
        session.setMaxInactiveInterval(Integer.valueOf(INDEX_SESSION_TIMEOUT));

        modelAndView.addObject("INDEXUSER", IndexUserConstant.getIndexUserByName(indexUsername));
        modelAndView.setViewName("index/index_chat");
        return modelAndView;
    }

    /**
     * 跳转到后台用户聊天页面
     * @param adminUsername
     * @return
     */
    @RequestMapping("/admin/chat/{adminUsername}")
    public ModelAndView showAdminChatPage(@PathVariable String adminUsername){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin_chat");
        modelAndView.addObject("ADMINUSERNAME",adminUsername);
        return modelAndView;
    }
}
