package com.landasoft.taoj.service.impl;

import com.github.pagehelper.PageHelper;
import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.mapper.TQuestionInfoMapper;
import com.landasoft.taoj.pojo.TQuestionInfo;
import com.landasoft.taoj.pojo.TQuestionInfoExample;
import com.landasoft.taoj.service.QuestionService;
import com.landasoft.taoj.utils.IDUtils;
import com.landasoft.taoj.utils.MyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 问题Service接口实现
 * @author zhaoyuan
 * @date 2020,July 7 11:34 am
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private TQuestionInfoMapper questionInfoMapper;

    @Override
    public LayuiTableResult getQuestionList(Integer pageNum, Integer pageSize, TQuestionInfo questionInfo) {
        //处理分页参数
        if(null == pageNum)
            pageNum = 1;
        if(null == pageSize)
            pageSize = 10;

        //分页
        PageHelper.startPage(pageNum,pageSize);

        //处理查询条件
        TQuestionInfoExample example = new TQuestionInfoExample();
        TQuestionInfoExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(questionInfo.getName()))
            criteria.andNameEqualTo(questionInfo.getName());
        
        if(StringUtils.isNotBlank(questionInfo.getState()))
            criteria.andStateEqualTo(questionInfo.getState());
            
        //执行查询
        List<TQuestionInfo> questionInfoList =
                questionInfoMapper.selectByExample(example);

        if(null == questionInfoList)
            throw new RuntimeException("Mybatis package exception");

        //返回的结果
        LayuiTableResult layuiTableResult = new LayuiTableResult();
        layuiTableResult.setData(questionInfoList);
        layuiTableResult.setCount(questionInfoList.size());

        return layuiTableResult;
    }

    @Override
    public MyResult addQuestion(TQuestionInfo questionInfo) {
        //校验参数
        if(StringUtils.isBlank(questionInfo.getName()))
            throw new RuntimeException("Must param question name is black!");

        //补全属性
        questionInfo.setId(IDUtils.getGeneId());
        questionInfo.setState("01");//问题状态 01：有效 02：过期
        questionInfo.setCreated(new Date());
        questionInfo.setUpdated(new Date());
        
        //执行插入
        int iResult = questionInfoMapper.insert(questionInfo);

        if(0 == iResult)
            return MyResult.build(101,"no add any record!");

        return MyResult.ok();
    }

    @Override
    public MyResult updateQuestion(TQuestionInfo questionInfo) {
        return null;
    }

    @Override
    @Transactional
    public MyResult updateQuestionWithState(String qid, String state) {
        if(StringUtils.isBlank(qid))
            throw new RuntimeException("Must param question id is black!");
        if(StringUtils.isBlank(state))
            throw new RuntimeException("Must param question state is black");

        TQuestionInfo questionInfo = questionInfoMapper.selectByPrimaryKey(qid);

        if(null == questionInfo)
            return MyResult.build(104,"No records were found according to the id!");

        questionInfo.setState(state);

        int iResult = questionInfoMapper.updateByPrimaryKey(questionInfo);

        if(0 == iResult)
            return MyResult.build(103,"Update db error!");

        return MyResult.ok();
    }

}
