package com.landasoft.taoj.constant;

import com.landasoft.taoj.mypojo.IndexUser;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟一些门户用户的信息
 */
public class IndexUserConstant {

    private static Map<String, IndexUser> indexUserMap = new HashMap<>();

    private static IndexUser tongliya = new IndexUser("tongliya","200808028","yaya","https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=452267795,2156448420&fm=58","佟丽娅");
    private static IndexUser zhangtianai = new IndexUser("zhangtianai","19880828","3u8633","https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1191483309,2243019881&fm=26&gp=0.jpg","张天爱");
    private static IndexUser lixin = new IndexUser("lixin","20190716","tianxiaoer","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594989718178&di=f3658ce2573b675261fcf2b6e80525cb&imgtype=0&src=http%3A%2F%2Fp0.ifengimg.com%2Fpmop%2F2018%2F0925%2FE98B73B9CBEB89B68BE3256CFBE7A3D276B98E04_size94_w1024_h683.jpeg","李沁");

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    public static IndexUser getIndexUserByName(String username){
        if(StringUtils.isBlank(username))
            throw new RuntimeException("Must param username is black!");

        indexUserMap.putIfAbsent("tongliya",tongliya);
        indexUserMap.putIfAbsent("zhangtianai",zhangtianai);
        indexUserMap.putIfAbsent("lixin",lixin);

        return indexUserMap.get(username);
    }

    public static Map<String,IndexUser> getIndexUserMap(){
        indexUserMap.putIfAbsent("tongliya",tongliya);
        indexUserMap.putIfAbsent("zhangtianai",zhangtianai);
        indexUserMap.putIfAbsent("lixin",lixin);

        return indexUserMap;
    }

    public static IndexUser getIndexUserByChinesname(String username){
        if(StringUtils.isBlank(username))
            throw new RuntimeException("Must param username is black!");

        indexUserMap.putIfAbsent("佟丽娅",tongliya);
        indexUserMap.putIfAbsent("张天爱",zhangtianai);
        indexUserMap.putIfAbsent("李沁",lixin);

        return indexUserMap.get(username);
    }
}
