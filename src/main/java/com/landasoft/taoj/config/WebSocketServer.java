package com.landasoft.taoj.config;

import com.landasoft.taoj.common.pojo.*;
import com.landasoft.taoj.constant.IndexUserConstant;
import com.landasoft.taoj.constant.TaojConstant;
import com.landasoft.taoj.mypojo.IndexUser;
import com.landasoft.taoj.service.AdminUserService;
import com.landasoft.taoj.service.QuestionService;
import com.landasoft.taoj.utils.DateUtils;
import com.landasoft.taoj.utils.JsonUtils;
import com.landasoft.taoj.utils.MyResult;
import com.landasoft.taoj.utils.WebSocketUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * webscoket 相关
 * @author zhaoyuan
 * @date 2020,July 14
 */
@ServerEndpoint(value = "/chat/ws")
@Component
public class WebSocketServer {

    private static final Logger log = Logger.getLogger(WebSocketServer.class);
    //记录门户用户在线数
    private static final AtomicInteger indexOnLineCount = new AtomicInteger(0);
    //记录管理用户在线数
    private static final AtomicInteger adminOnLineCount = new AtomicInteger(0);
    //存放门户用户，key-用户名 value-通道
    private static final ConcurrentHashMap<String, Session> indexSessionMap = new ConcurrentHashMap<>();
    //存放门户用户，key-用户名 value-通道
    private static final ConcurrentHashMap<String,Session> adminSessionMap = new ConcurrentHashMap<>();
    //用于门户用户是否在和管理用户聊天
    private static final ConcurrentHashMap<String,String> indexAndAdminMap = new ConcurrentHashMap<>();
    //储存管理用户的空闲session
    private static final ConcurrentHashMap<String,Session> adminFreeSession = new ConcurrentHashMap<>();
    //队列
    ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();

    /**
     * 服务端初始化
     */
    @PostConstruct
    public void init(){
        log.info("webscoket加载......");
    }

    /**
     * 客户端与服务端建立连接
     * @param session 当前客户端与服务端建立的通道
     */
    @OnOpen
    public void open(Session session){
        //获取参数
        String[] paramsArray = WebSocketUtils.getSocketTypeAndUName(session.getQueryString());
        String type = paramsArray[0];
        String username = paramsArray[1];

        //存入各自的通道
        if(type.equals(TaojConstant.getName("INDEX_USER_TYPE"))){//门户用户
            indexSessionMap.putIfAbsent(username,session);
            int tCount = indexOnLineCount.incrementAndGet();
            log.info("门户用户有新连接加入，当前连接数为:"+tCount);

            //处理系统消息内容,告知对方通道建立成功
            String msgResult = WebSocketUtils.getSendMessage(0, "通道建立成功，该通道是一个长连接😶");
            if(null != session){
                sendMessage(session,msgResult);
            }
        } else if (type.equals(TaojConstant.getName("ADMIN_USER_TYPE"))){//后台用户
            adminSessionMap.putIfAbsent(username,session);
            adminFreeSession.putIfAbsent(username,session);
            int tCount = adminOnLineCount.incrementAndGet();
            log.info("管理用户有新连接加入，当前连接数为:"+tCount);
        }

    }

    @OnClose
    public void close(Session session){
        //判断到底是门户用户关闭，还是管理用户关闭

        //门户用户
        for (String s : indexSessionMap.keySet()) {
            Session indexSession0 = indexSessionMap.get(s);
            if(indexSession0.toString().equals(session.toString())){
                IndexUser indexUser = IndexUserConstant.getIndexUserByName(s);
                String alias = indexUser.getAlias();

                //关闭门户用户和服务端的通道,这是必须要关闭的
                indexSessionMap.remove(s);
                //关闭门户用户和管理用户是否在聊天的标识
                String adminUsername = indexAndAdminMap.get(alias);
                if(StringUtils.isNotBlank(adminUsername)){
                    adminFreeSession.putIfAbsent(adminUsername,adminSessionMap.get(adminUsername));
                    indexAndAdminMap.remove(alias);
                }
                //关闭等待队列
                concurrentLinkedQueue.remove(alias);

                int tCount = indexOnLineCount.decrementAndGet();
                log.info("门户用户有新连接关闭，当前连接数为:"+tCount);
            }
        }

        //管理用户
        for (String s : adminSessionMap.keySet()) {
            Session adminSession = adminSessionMap.get(s);
            if(adminSession.toString().equals(session.toString())){
                adminSessionMap.remove(s);
                adminFreeSession.remove(s);

                for (String s1 : indexAndAdminMap.keySet()) {
                    String adminUsername = indexAndAdminMap.get(s1);
                    if(s.equals(adminUsername)){
                        indexAndAdminMap.remove(s1);
                    }
                }

                int tCount = adminOnLineCount.decrementAndGet();
                log.info("管理用户有新连接关闭，当前连接数为:"+tCount);
                
            }
        }

    }

    /**
     * 接收客户端发送的消息
     * @param session   客户端与服务端当前通道
     * @param message   消息体
     */
    @OnMessage
    public void message(Session session,String message){
        log.info("接收到客户端消息:"+message);

        //将消息体转为对象
        LayuiIMSendMessageResult layuiIMSendMessageResult = JsonUtils.jsonToPojo(message, LayuiIMSendMessageResult.class);

        //处理要发送的消息

        //消息内容
        String content = layuiIMSendMessageResult.getData().getMine().getContent();
        //逻辑上谁发的消息
        String mineUsername = layuiIMSendMessageResult.getData().getMine().getUsername();
        //消息类型，目前有两种，chatMessage、adminMessage
        String type = layuiIMSendMessageResult.getType();

        //*********************管理用户发的消息********************
        if(TaojConstant.getName("ADMIN_MESSAGE").equals(type)){
            LayuiIMSendData sendData = layuiIMSendMessageResult.getData();
            Mine mine = sendData.getMine();
            To to = sendData.getTo();

            String alias0 = mine.getUsername();//
            String alias1 = to.getUsername();//

            /*AdminUserService adminUserService = SpringContextUtils.getBean(AdminUserService.class);
            String username = adminUserService.getUserNameByAlias(alias0);
            if(null == username){
                return;
            }*/
            //当管理用户发送的消息是“结束回答”时,将移除门户用户和管理用户的标志
            if(TaojConstant.getName("ADMIN_END_CHAT").equals(content)){
                AdminUserService adminUserService = SpringContextUtils.getBean(AdminUserService.class);
                String adminUsername = adminUserService.getUserNameByAlias(alias0);

                //添加管理用户的空闲通道等等
                adminFreeSession.putIfAbsent(adminUsername,adminSessionMap.get(adminUsername));
                indexAndAdminMap.remove(alias1);

                //发送响应的信息
                String sendIndexMessage = WebSocketUtils.getSendMessage(0, "结束与" + adminUsername + "的通话，与该用户的通道标识被关闭");

                IndexUser indexUser = IndexUserConstant.getIndexUserByChinesname(alias1);
                String indexUsername = indexUser.getUsername();
                Session indexSession = indexSessionMap.get(indexUsername);
                //发送
                if(null != indexSession){
                    sendMessage(indexSession,sendIndexMessage);
                }

                String sendAdminMessage = WebSocketUtils.getSendMsgWithIndexUser(2, "系统消息，结束成功，通道标识被移除", indexUser);

                if(null != session){
                    sendMessage(session,sendAdminMessage);
                }

                return;
            }

            IndexUser indexUser = IndexUserConstant.getIndexUserByChinesname(alias1);

            Session indexSession = indexSessionMap.get(indexUser.getUsername());

            String msgResult = WebSocketUtils.getSendMessage(2, content);

            if(null != indexSession){
                //发送
                sendMessage(indexSession,msgResult);
            }

        }else{
            //*********************门户用户发的消息********************
            if(null != indexAndAdminMap.get(mineUsername)){
                String msgResult = WebSocketUtils.getSendMsgWithIndexUser(2, content, IndexUserConstant.getIndexUserByChinesname(mineUsername));

                //发送
                String str = indexAndAdminMap.get(mineUsername);
                Session adminSession = adminSessionMap.get(str);

                if(null != adminSession){
                    sendMessage(adminSession,msgResult);
                }

            }else{
                if(!TaojConstant.getName("index_chat_man_answer").equals(content)){//自动回复
                    //content不等于“人工客服”时，系统自动回复
                    QuestionService questionService = SpringContextUtils.getBean(QuestionService.class);

                    String msgResult = "";

                    //判断是直接回复问题解决方案，还是查索引库
                    MyResult myResult = questionService.getQuestionItemByqname(content);
                    if(null == myResult.getData()){//查询索引库
                        MyResult myResultNo = questionService.getQuestionListWithSolrByName(content);
                        msgResult = WebSocketUtils.getSendMessage(1, myResultNo.getData());
                    }else{//直接回复问题解决方案
                        msgResult = WebSocketUtils.getSendMessage(1, myResult.getData());
                    }

                    //发送
                    if(null != session){
                        sendMessage(session,msgResult);
                    }

                }else{//人工客服接入
                    //假设现在只要5个人工客服,如果5个人工客服都占线，新加入的门户用户将加入等待队列
                    //尝试取adminFreeSession中的value值
                    
                    //判断是不是客服应该在线的时间
                    Integer admin_work_time_start = Integer.valueOf(TaojConstant.getName("ADMIN_WORK_TIME_START"));
                    Integer admin_work_time_end = Integer.valueOf(TaojConstant.getName("ADMIN_WORK_TIME_END"));
                    int hour = DateUtils.getDayHour();
                    int weekDay = DateUtils.getWeekDay();
                    if(hour > admin_work_time_end || hour < admin_work_time_start || weekDay == 6 || weekDay == 7){
                        String msgResult = WebSocketUtils.getSendMessage(0, TaojConstant.getName("ADMIN_WORK_TIME_SHOW"));
                        if(null != session){
                            sendMessage(session,msgResult);
                        }
                        return;
                    }

                    if(0 == adminFreeSession.size()){
                        //没有空闲的人工客服
                        String msgResult = WebSocketUtils.getSendMessage(0, "当前客服全部占线,队列等待人数" + concurrentLinkedQueue.size() + 1);

                        if(null != msgResult){
                            sendMessage(session,msgResult);
                        }

                        concurrentLinkedQueue.add(mineUsername);

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                boolean flag = false;
                                while (true){
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    for (String key : adminFreeSession.keySet()) {
                                        Session adminSession = adminFreeSession.get(key);
                                        if(null != adminSession){
                                            flag = true;

                                            indexAndAdminMap.putIfAbsent(mineUsername, key);
                                            adminFreeSession.remove(key);

                                            String msgResult = WebSocketUtils.getSendMessage(2, "你好，" + key + "为你服务，已有什么需要帮助的");

                                            if(null != session){
                                                sendMessage(session,msgResult);//给门户用户发消息，说人工客服接入成功
                                            }

                                            String msgResult1 = WebSocketUtils.getSendMsgWithIndexUser(0, mineUsername + "提问", IndexUserConstant.getIndexUserByChinesname(mineUsername));

                                            if(null != adminSession){
                                                sendMessage(adminSession,msgResult1);//给管理用户发消息，说门户用户有人要提问
                                            }

                                            concurrentLinkedQueue.remove(mineUsername);

                                            break;
                                        }
                                    }

                                    if(flag){
                                        break;
                                    }
                                }
                            }
                        });
                        t.start();
                    }else{
                        Session adminSession = null;
                        String key = "";
                        for (String key1 : adminFreeSession.keySet()) {
                            adminSession = adminFreeSession.get(key1);
                            if(null != adminSession){
                                key = key1;
                                break;
                            }
                        }

                        //String tempAdminSessionKey = "sfy";
                        //Session adminSession = adminSessionMap.get(adminSession);

                        indexAndAdminMap.put(mineUsername,key);
                        adminFreeSession.remove(key);

                        String msgResult = WebSocketUtils.getSendMessage(2, "你好，" + key + "为你服务，已有什么需要帮助的");

                        if(null != session){
                            sendMessage(session,msgResult);//给门户用户发消息，说人工客服接入成功
                        }

                        String msgResult1 = WebSocketUtils.getSendMsgWithIndexUser(0, mineUsername + "提问", IndexUserConstant.getIndexUserByChinesname(mineUsername));

                        if (null != adminSession){
                            sendMessage(adminSession,msgResult1);//给管理用户发消息，说门户用户有人要提问
                        }
                    }

                }
            }
        }

    }

    /**
     * 向客户端发送消息
     * @param session   各自通道
     * @param json   消息体
     */
    public void sendMessage(Session session,String json){
        try {
            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
