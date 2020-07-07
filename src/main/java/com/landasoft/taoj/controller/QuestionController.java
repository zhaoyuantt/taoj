package com.landasoft.taoj.controller;

import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.pojo.TQuestionInfo;
import com.landasoft.taoj.service.QuestionService;
import com.landasoft.taoj.utils.MyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 问题Controller
 *
 * @author zhaoyuan
 * @date 2020, June 7 15:13 pm
 */
@Controller
@RequestMapping("/admin")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    //Integer page,Integer limit
    /**
     * 问题列表查询
     * @param pageNum
     * @param pageSize
     * @param questionInfo
     * @return
     */
    @RequestMapping(value = "/question/list",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public LayuiTableResult getQuestionList(@RequestParam(value = "page") Integer pageNum,
                                            @RequestParam(value = "limit") Integer pageSize,
                                            TQuestionInfo questionInfo) {
        LayuiTableResult layuiTableResult = questionService.getQuestionList(pageNum, pageSize, questionInfo);
        return layuiTableResult;
    }

    /**
     * 添加一个问题文档记录
     * @param questionInfo
     * @return
     */
    @RequestMapping(value = "/question/A",method = RequestMethod.POST)
    @ResponseBody
    public MyResult addQuestion(TQuestionInfo questionInfo){
        return questionService.addQuestion(questionInfo);
    }

    @RequestMapping("/question/U/state/{qid}/{qstate}")
    @ResponseBody
    public MyResult updateQuestionWithState(@PathVariable String qid,@PathVariable String qstate){
        return questionService.updateQuestionWithState(qid,qstate);
    }
}
