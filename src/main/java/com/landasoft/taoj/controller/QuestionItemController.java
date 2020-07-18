package com.landasoft.taoj.controller;

import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.pojo.TQuestionItem;
import com.landasoft.taoj.service.QuestionItemService;
import com.landasoft.taoj.utils.MyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 问题明细Controller
 * @author zhaoyuan
 * @date 2020,July 8
 */
@Controller
@RequestMapping("/admin")
public class QuestionItemController {

    @Autowired
    private QuestionItemService questionItemService;

    @RequestMapping(value = "/question/item/A",method = RequestMethod.POST)
    @ResponseBody
    public MyResult addQuestionItem(TQuestionItem questionItem){
        return questionItemService.addQuestionItem(questionItem);
    }

    @RequestMapping(value = "/question/item/list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public LayuiTableResult getQuestionItemList(@RequestParam(value = "page",defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "limit",defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "qid",required = true) String qid,
                                                TQuestionItem questionItem){
        return questionItemService.getQuestionItemList(pageNum,pageSize,qid,questionItem);
    }

    @RequestMapping("/question/item/D/{id}")
    @ResponseBody
    public MyResult deleteQuestionItem(@PathVariable String id){
        return questionItemService.deleteQuestionItemById(id);
    }

    @RequestMapping("/question/item/E")
    @ResponseBody
    public MyResult updateQuestionItem(TQuestionItem questionItem){
        return questionItemService.updateQuestionItem(questionItem);
    }

}
