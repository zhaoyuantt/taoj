<%--
  Created by Eclipse.
  User: zhaoyuan
  Date: 2020/07/14
  Time: 5:11 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>index-chat</title>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/jQuery/jquery-1.8.3.js"></script>
    <style type="text/css">

    </style>
</head>
<body>
    <h3>欢迎 ${INDEXUSERNAME} 使用本系统！！</h3>

    <div id="content" style="border: 1px solid black; width: 400px;height: 300px;float: left;"></div>
    <div id="userList" style="border: 1px solid black; width: 130px; height: 300px;float:left;margin-left:20px;"></div>
    <div style="clear: both;margin-top: 10px;padding-top: 20px;padding-left:120px;">
        <input id="msg"/>
        <button id="send">send</button>
    </div>

<script type="text/javascript">
    $(function(){
        //进入聊天页面就打开聊天通道
        var ws;
        var username = '${INDEXUSERNAME}';
        var target = "ws://127.0.0.1:8080//chat/ws?username="+username;

        if ('WebSocket' in window) {
            ws = new WebSocket(target);
            //connect();
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(target);
            //connect();
        } else {
            alert('WebSocket is not supported by this browser.');
            return;
        }

        //接收服务端返回消息
        ws.onmessage=function(event){
            /*eval("var msg="+event.data+";");
            //欢迎消息
            if(undefined != msg.welcome){
                $('#content').append(msg.welcome+"<br/>");
            }
            //用户列表
            if(undefined != msg.usernames){
                $('#userList').html("");
                $(msg.usernames).each(function(){
                    $('#userList').append("<input type=checkbox value='"+this+"'/>"+this+"<br/>");
                });
            }
            //聊天内容
            if(undefined != msg.content){
                $('#content').append(msg.content);
            }*/
            console.log(event);
        }

        //为button按钮绑定点击事件
        $('#send').click(function(){
            /* var msg = $('#msg').val();
            //发送数据
            ws.send(msg);
            //清空Input
            $('#msg').val(""); */

            var ss = $("#userList :checked");
            //console.log(ss.length);
            var msg = $('#msg').val();
            var obj = null;
            //群聊
            if(0 == ss.length){
                obj = {
                    msg:msg,
                    type:1   //广播
                }
            }else{
                var to = $("#userList :checked").val();
                obj = {
                    msg:msg,
                    type:2,   //单聊
                    to:to
                }
            }
            //将js对象转换为json字符串
            var str = JSON.stringify(obj);
            //发送
            ws.send(str);
            //清空Input
            $('#msg').val("");
        });

    });
</script>
</body>
</html>