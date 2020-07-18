package com.landasoft.taoj.service.impl;

import com.github.pagehelper.PageHelper;
import com.landasoft.taoj.common.pojo.LayuiTableResult;
import com.landasoft.taoj.constant.TaojConstant;
import com.landasoft.taoj.mapper.TQuestionInfoMapper;
import com.landasoft.taoj.mapper.TQuestionItemMapper;
import com.landasoft.taoj.pojo.TQuestionInfo;
import com.landasoft.taoj.pojo.TQuestionInfoExample;
import com.landasoft.taoj.pojo.TQuestionItem;
import com.landasoft.taoj.pojo.TQuestionItemExample;
import com.landasoft.taoj.service.QuestionService;
import com.landasoft.taoj.utils.IDUtils;
import com.landasoft.taoj.utils.MyResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 问题Service接口实现
 * @author zhaoyuan
 * @date 2020,July 7 11:34 am
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger log = Logger.getLogger(QuestionServiceImpl.class);

    @Autowired
    private TQuestionInfoMapper questionInfoMapper;
    @Autowired
    private TQuestionItemMapper questionItemMapper;
    @Autowired
    private SolrClient solrClient;

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
            criteria.andNameLike("%"+questionInfo.getName()+"%");
        
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
    @Transactional
    public MyResult addQuestion(TQuestionInfo questionInfo) {
        //校验参数
        if(StringUtils.isBlank(questionInfo.getName()))
            throw new RuntimeException("Must param question name is black!");

        //问题主键
        String id = IDUtils.getGeneId();

        try {
            //添加索引
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.setField("id",IDUtils.genItemId());
            solrInputDocument.setField("q_id",id);
            solrInputDocument.setField("q_name",questionInfo.getName());
            solrClient.add(solrInputDocument);
            solrClient.commit();
            log.info("名称为\""+questionInfo.getName()+"\"问题文档索引添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.build(201,
                    "Solr exception when adding index to document");
        }

        //补全属性
        questionInfo.setId(id);
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
    @Transactional
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

    @Override
    @Transactional
    public MyResult updateQuestionWithNB(String qid, String colume, String value) {
        if(StringUtils.isBlank(qid))
            throw new RuntimeException("Must param qid is black!");
        if(StringUtils.isBlank(colume))
            throw new RuntimeException("Must param colume name is black!");
        if(StringUtils.isBlank(value))
            throw new RuntimeException("Must param colume value is black!");

        //编辑索引
        //以下是通过先根据q_id查询文档后，在创建SolrInputDocument对象，执行add方法编辑
        //也可以先删除，在添加
        if("name".equals(colume))
            try {
                //创建SolrQuery对象
                SolrQuery solrQuery = new SolrQuery();
                //向SolrQuery中添加查询条件、过滤条件......
                solrQuery.setQuery("q_id:"+qid);
                //执行查询
                QueryResponse queryResponse = solrClient.query(solrQuery);
                //获取查询结果
                SolrDocumentList solrDocumentList = queryResponse.getResults();
                SolrDocument solrDocument = solrDocumentList.get(0);

                //执行更新
                SolrInputDocument solrInputDocument = new SolrInputDocument();
                solrInputDocument.setField("id",solrDocument.get("id"));
                solrInputDocument.setField("q_id",qid);
                solrInputDocument.setField("q_name",value);
                solrClient.add(solrInputDocument);
                solrClient.commit();
                log.info("名称为\""+value+"\"问题文档索引编辑成功");
            } catch (Exception e) {
                e.printStackTrace();
                return MyResult.build(201,
                        "Solr exception when updating index to document");
            }

        TQuestionInfo questionInfo = questionInfoMapper.selectByPrimaryKey(qid);

        if(null == questionInfo)
            return MyResult.build(104,"No records were found according to the id!");

        if("name".equals(colume))
            questionInfo.setName(value);
        if ("remark".equals(colume))
            questionInfo.setRemark(value);

        int iResult = questionInfoMapper.updateByPrimaryKey(questionInfo);

        if(0 == iResult)
            return MyResult.build(103,"Update db error!");

        return MyResult.ok();
    }

    @Override
    @Transactional
    public MyResult deleteQuestionAndQItemByQid(String qid) {
        if(StringUtils.isBlank(qid))
            throw new RuntimeException("Must param question id is black!");

        //删除索引
        try {
            //通过查询条件删除
            solrClient.deleteByQuery("q_id:"+qid);
            //提交
            solrClient.commit();
            log.info("Id为\""+qid+"\"问题文档索引删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.build(201,
                    "Solr exception when deleting index to document");
        }

        int dqResult = questionInfoMapper.deleteByPrimaryKey(qid);

        TQuestionItemExample questionItemExample = new TQuestionItemExample();
        TQuestionItemExample.Criteria criteria = questionItemExample.createCriteria();
        criteria.andQIdEqualTo(qid);

        questionItemMapper.deleteByExample(questionItemExample);

        if(0 == dqResult)
            return MyResult.build(102,"No question records were deleteed!");

        return MyResult.ok();
    }

    @Override
    public MyResult getQuestionListWithSolrByName(String name) {
        if (StringUtils.isBlank(name))
            throw new RuntimeException("Must param name is black!");

        //查询索引库
        SolrQuery solrQuery = new SolrQuery();
        //查询那个域
        solrQuery.setQuery("q_name:"+name);
        //开启高亮显示
        solrQuery.setHighlight(true);
        //要高亮显示的域
        solrQuery.addHighlightField("q_name");
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</span>");

        String content = "";

        //执行查询
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);

            //取查询结果
            SolrDocumentList solrDocumentList = queryResponse.getResults();

            if(null != solrDocumentList && 0 == solrDocumentList.size())
                content = TaojConstant.getName("SOLR_QUERY_INDEX_NODATA");

            for (SolrDocument solrDocument : solrDocumentList) {

                //设置class、id属性，绑定点击事件，鼠标图标为小手
                content = content + "<span id = \""+solrDocument.get("q_id")+"\" class=\"taoj-solr-highlight\" onclick=\"showMsgLayuiImMsgTextArea('"+solrDocument.get("q_id")+"')\" style=\"cursor: pointer\">";

                //取高亮结果
                Map<String, Map<String, List<String>>> highlightingMap = queryResponse.
                        getHighlighting();

                List<String> list = highlightingMap.get(solrDocument.get("id")).get("q_name");

                String qname = null;
                if(null != list && list.size()>0){
                    qname = list.get(0);
                } else{
                    qname = (String) solrDocument.get("q_name");
                }

                content = content + qname + "</span></br>";

                System.out.println(solrDocument.get("id"));
                System.out.println(solrDocument.get("q_id"));
                System.out.println(qname);
            }

            log.info("拼接的问题:"+content);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.build(201,
                    "Solr exception when select index to document");
        }


        return MyResult.ok(content);
    }

    @Override
    public MyResult getQuestionItemByqname(String qname) {
        if(StringUtils.isBlank(qname))
            return MyResult.ok();

        TQuestionInfoExample questionInfoExample = new TQuestionInfoExample();
        TQuestionInfoExample.Criteria questionInfoCriteria = questionInfoExample.createCriteria();
        questionInfoCriteria.andNameEqualTo(qname);
        questionInfoCriteria.andStateEqualTo("01");

        List<TQuestionInfo> questionInfoList = questionInfoMapper.selectByExample(questionInfoExample);

        //输出的内容
        String content = "";

        if(0 == questionInfoList.size())
            //进行检索
            return MyResult.ok();
        else {
            TQuestionInfo questionInfo = questionInfoList.get(0);
            //问题id
            String id = questionInfo.getId();

            TQuestionItemExample questionItemExample = new TQuestionItemExample();
            TQuestionItemExample.Criteria questionItemCriteria = questionItemExample.createCriteria();
            questionItemCriteria.andQIdEqualTo(id);
            questionItemCriteria.andIStateEqualTo("01");

            List<TQuestionItem> questionItemList = questionItemMapper.selectByExample(questionItemExample);

            if(0 == questionItemList.size()){
                return MyResult.ok(TaojConstant.getName("NO_QUESTION_ITEM"));
            }else{
                for (TQuestionItem questionItem : questionItemList) {
                    String iName = questionItem.getiName();
                    content = content + iName + "</br>";
                }
            }

        }

        return MyResult.ok(content);
    }

}
