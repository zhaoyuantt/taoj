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

    /**
     * 编辑一个问题文档记录的名称和备注
     * @param qid 文档id
     * @param colume 文档属性名
     * @param value 值
     * @return
     */
    MyResult updateQuestionWithNB(String qid,String colume,String value);

    /**
     * 删除问题和问题明细
     * @param qid
     * @return
     */
    MyResult deleteQuestionAndQItemByQid(String qid);

    /**
     * 根据用户输入的内容，查询索引库，返回问题列表
     * @param name   用户输入的问题
     * @return
     */
    MyResult getQuestionListWithSolrByName(String name);

    /**
     * 根据问题名称查询问题解决方案列表,如果没有查询到，将查询索引库
     * @param qname
     * @return
     */
    MyResult getQuestionItemByqname(String qname);

}
