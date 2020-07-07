package com.landasoft.taoj.service;

import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.pojo.TQuestionInfo;
import com.landasoft.taoj.utils.MyResult;

/**
 * 问题Service接口
 * @author zhaoyuan
 * @date 2020,July 7 11:28 pm
 */
public interface QuestionService {

    /**
     * 问题列表查询
     * @param pageNum 当前第几页
     * @param pageSize 每页显示多少条数据
     * @param questionInfo 查询条件
     * @return
     */
    LayuiTableResult getQuestionList(Integer pageNum, Integer pageSize, TQuestionInfo questionInfo);

    /**
     * 添加一个问题文档
     * @param questionInfo
     * @return
     */
    MyResult addQuestion(TQuestionInfo questionInfo);

    /**
     * 编辑一个问题文档
     * @param questionInfo
     * @return
     */
    MyResult updateQuestion(TQuestionInfo questionInfo);

    /**
     * 编辑一个问题文档的状态
     * @param qid
     * @param state
     * @return
     */
    MyResult updateQuestionWithState(String qid,String state);

}
