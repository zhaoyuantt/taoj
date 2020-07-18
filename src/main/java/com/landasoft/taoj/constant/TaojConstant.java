package com.landasoft.taoj.constant;

/**
 * 系统常量枚举类
 * @author zhaoyuan
 * @date 2020,July 6 3:20 pm
 */
public enum TaojConstant {

    MAN_ANSWER("index_chat_man_answer","人工客服"),
    SOLR_QUERY_INDEX_NODATA("SOLR_QUERY_INDEX_NODATA","自动回复小姐姐，目前还无法识别你的问题，在凿壁借光的学习中，期待下次为你回答，😍"),
    NO_QUESTION_ITEM("NO_QUESTION_ITEM","抱歉，没有查询到相关问题的解决方案，客服小姐姐在玩命录入(๑•̀ㅂ•́)و✧"),
    INDEX_USER_TYPE("INDEX_USER_TYPE","index"),
    ADMIN_USER_TYPE("ADMIN_USER_TYPE","admin"),
    ADMIN_MESSAGE("ADMIN_MESSAGE","adminMessage"),
    ADMIN_END_CHAT("ADMIN_END_CHAT","结束回答"),
    ADMIN_WORK_TIME_START("ADMIN_WORK_TIME_START","9"),
    ADMIN_WORK_TIME_END("ADMIN_WORK_TIME_END","18"),
    ADMIN_WORK_TIME_SHOW("ADMIN_WORK_TIME_SHOW","人工客服在线时间,工作日每天9：00-18：00");

    String code;
    String name;

    TaojConstant(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String code){
        for(TaojConstant evenEnum : TaojConstant.values()){
            if(evenEnum.getCode().equals(code)){
                return evenEnum.getName();
            }
        }
        return null;
    }
}
