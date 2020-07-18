package com.landasoft.taoj.service.impl;

import com.github.pagehelper.PageHelper;
import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.mapper.TQuestionItemMapper;
import com.landasoft.taoj.pojo.TQuestionItem;
import com.landasoft.taoj.pojo.TQuestionItemExample;
import com.landasoft.taoj.service.QuestionItemService;
import com.landasoft.taoj.utils.IDUtils;
import com.landasoft.taoj.utils.MyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 问题明细Service接口实现
 * @author zhaoyuan
 * @date 2020,July 8
 */
@Service
public class QuestionItemServiceImpl implements QuestionItemService {

    @Autowired
    private TQuestionItemMapper questionItemMapper;

    @Override
    @Transactional
    public MyResult addQuestionItem(TQuestionItem questionItem) {
        if(StringUtils.isBlank(questionItem.getqId()))
            throw new RuntimeException("Must param question id is black!");
        if(StringUtils.isBlank(questionItem.getiName()))
            throw new RuntimeException("Must param question item name is black!");

        questionItem.setId(IDUtils.getGeneId());
        //questionItem.setqId(qid);
        questionItem.setiState("01");//状态 01:有效 02：过期
        questionItem.setCreated(new Date());
        questionItem.setUpdated(new Date());

        int iResult = questionItemMapper.insert(questionItem);

        if(0 == iResult)
            return MyResult.build(101,"No records were added to the database!");

        return MyResult.ok();
    }

    @Override
    public LayuiTableResult getQuestionItemList(Integer pageNum, Integer pageSize,String qid, TQuestionItem questionItem) {
        if(StringUtils.isBlank(qid))
            throw new RuntimeException("Must param question is is black!");

        LayuiTableResult layuiTableResult = new LayuiTableResult();

        PageHelper.startPage(pageNum,pageSize);

        TQuestionItemExample example = new TQuestionItemExample();
        TQuestionItemExample.Criteria criteria = example.createCriteria();

        criteria.andQIdEqualTo(qid);

        if(StringUtils.isNotBlank(questionItem.getiName()))
            criteria.andINameLike("%"+questionItem.getiName()+"%");
        if(StringUtils.isNotBlank(questionItem.getiState()))
            criteria.andIStateEqualTo(questionItem.getiState());

        List<TQuestionItem> questionItemList = questionItemMapper.selectByExample(example);

        if(null == questionItemList || 0 == questionItemList.size()){
            layuiTableResult.setData(questionItemList);
            layuiTableResult.setCount(0);
            return layuiTableResult;
        }

        layuiTableResult.setCount(questionItemList.size());
        layuiTableResult.setData(questionItemList);

        return layuiTableResult;
    }

    @Override
    @Transactional
    public MyResult deleteQuestionItemById(String id) {
        if(StringUtils.isBlank(id))
            throw new RuntimeException("Must param question item is null!");

        int dResult = questionItemMapper.deleteByPrimaryKey(id);

        if(0 == dResult)
            return MyResult.build(102,"No records were deleted!");

        return MyResult.ok();
    }

    @Override
    @Transactional
    public TQuestionItem getQuestionItemById(String id) {
        if(StringUtils.isBlank(id))
            throw new RuntimeException("Must param question item is null!");

        TQuestionItem questionItem = questionItemMapper.selectByPrimaryKey(id);

        if(null == questionItem)
            throw new RuntimeException("No records were found!");

        return questionItem;
    }

    @Override
    public MyResult updateQuestionItem(TQuestionItem questionItem) {
        if(StringUtils.isBlank(questionItem.getId()))
            throw new RuntimeException("Must param question item id is black!");
        if(StringUtils.isBlank(questionItem.getiName()))
            throw new RuntimeException("Must param question item name is black!");

        TQuestionItem tQuestionItem = getQuestionItemById(questionItem.getId());

        if(StringUtils.isNotBlank(questionItem.getiName()))
            tQuestionItem.setiName(questionItem.getiName());
        if(StringUtils.isNotBlank(questionItem.getRemark()))
            tQuestionItem.setRemark(questionItem.getRemark());
        if(StringUtils.isNotBlank(questionItem.getiState()))
            tQuestionItem.setiState(questionItem.getiState());

        tQuestionItem.setUpdated(new Date());

        int uResult = questionItemMapper.updateByPrimaryKey(tQuestionItem);

        if(0 == uResult)
            return MyResult.build(103,"No records were updated!");

        return MyResult.ok();
    }
}
