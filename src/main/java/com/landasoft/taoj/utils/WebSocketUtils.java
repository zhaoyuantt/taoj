package com.landasoft.taoj.utils;

import com.alibaba.fastjson.JSONObject;
import com.landasoft.taoj.common.pojo.LayuiIMReceiveMessageResult;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * WebSocket 相关
 * @author zhaoyuan
 * @date 2020,July 17
 */
public class WebSocketUtils {

    /**
     * 获取与websocket建立通道时参数type和username的值
     * @param str
     * @return
     */
    public static String[] getSocketTypeAndUName(String str){
        if(StringUtils.isBlank(str))
            throw new RuntimeException("Param is black!");

        String[] strSplit0 = str.split("&");

        String type = strSplit0[0].split("=")[1];
        String username = strSplit0[1].split("=")[1];

        String[] result = new String[strSplit0.length];

        result[0] = type;
        result[1] = username;

        return result;
    }
    
    public static void testIterMap(){
        ConcurrentHashMap<String,String> concurrentHashMap = new
                ConcurrentHashMap<>();
        concurrentHashMap.putIfAbsent("xian","西安");
        concurrentHashMap.putIfAbsent("lanzhou","兰州");

        for (String key : concurrentHashMap.keySet()) {
            String value = concurrentHashMap.get(key);
            if("兰州".equals(value))
                concurrentHashMap.remove(key);
        }
    }

    public static void testBlockingQueue(){
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();
        concurrentLinkedQueue.add("11");
        concurrentLinkedQueue.add("22");

        String poll = concurrentLinkedQueue.poll();

        System.out.println(poll);
    }

    /**
     * 处理发送给Layui im 的消息
     * @param msgType   消息类型 0:系统消息 1：自动回复消息 2：人工消息
     * @param msgData   消息内容
     * @return
     */
    public static String getSendMessage(Integer msgType,Object msgData){
        LayuiIMReceiveMessageResult layuiIMReceiveMessageResult = new LayuiIMReceiveMessageResult();
        layuiIMReceiveMessageResult.setMsgType(msgType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",msgData);
        layuiIMReceiveMessageResult.setMsgData(jsonObject);
        String json = JsonUtils.objectToJson(layuiIMReceiveMessageResult);
        return json;
    }

    /**
     * 处理发送给Layui im 的消息,多前台用户信息
     * @param msgType
     * @param msgData
     * @param obj
     * @return
     */
    public static String getSendMsgWithIndexUser(Integer msgType,Object msgData,Object obj){
        LayuiIMReceiveMessageResult layuiIMReceiveMessageResult = new
                LayuiIMReceiveMessageResult();
        layuiIMReceiveMessageResult.setMsgType(msgType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",msgData);
        jsonObject.put("indexuser",obj);
        layuiIMReceiveMessageResult.setMsgData(jsonObject);
        String json = JsonUtils.objectToJson(layuiIMReceiveMessageResult);
        return json;
    }



    public static void main(String[] args) {
        WebSocketUtils.getSocketTypeAndUName("type=index&username=sfy");
        WebSocketUtils.testIterMap();
        WebSocketUtils.testBlockingQueue();
    }
}
