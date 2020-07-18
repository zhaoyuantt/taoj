package com.landasoft.taoj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Solr java 客户端测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSolrJ {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void testAddSolrDocument(){
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.setField("id","12345678");
        solrInputDocument.setField("q_id","test002");
        solrInputDocument.setField("q_name","兰州");


        try {
            solrClient.add(solrInputDocument);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != solrClient){
                try {
                    solrClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testDeleteSole(){
        //solr 后台管理页面删除索引
       /*<delete>
            <query>q_name:兰州</query>
        </delete>
        <commit/>*/
    }


}
