package com.landasoft.taoj;

import com.landasoft.taoj.pojo.TQuestionInfo;
import com.landasoft.taoj.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQuestion {

    @Autowired
    private QuestionService questionService;

    @Test
    public void testSelectQuestionList(){
        TQuestionInfo questionInfo = new TQuestionInfo();
        questionInfo.setName("账号");
        questionService.getQuestionList(1,10,questionInfo);
    }
}
