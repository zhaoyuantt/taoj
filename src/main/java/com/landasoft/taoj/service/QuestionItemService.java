package com.landasoft.taoj.service;

import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.pojo.TQuestionItem;
import com.landasoft.taoj.utils.MyResult;

/**
 * 问题明细Service接口
 * @author zhaoyuan
 * @date 2020,July 8
 */
public interface QuestionItemService {

    MyResult addQuestionItem(TQuestionItem questionItem);

    /**
     *
     * @param pageNum   当前第几页
     * @param pageSize   每页显示多少条数据
     * @param qid   问题id
     * @param questionItem   查询条件
     * @return
     */
    LayuiTableResult getQuestionItemList(Integer pageNum, Integer pageSize,String qid,TQuestionItem questionItem);

    /**
     *
     * @param id   明细Id
     * @return
     */
    MyResult deleteQuestionItemById(String id);

    TQuestionItem getQuestionItemById(String id);

    MyResult updateQuestionItem(TQuestionItem questionItem);
}
