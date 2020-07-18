<%--
  Created by Eclipse.
  User: zhaoyuan
  Date: 2020/07/17
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/layui.layim-v3.0.1/layui/css/layui.css" media="all">
    <style type="text/css">

    </style>
</head>
<body>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/jQuery/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/layui.layim-v3.0.1/layui/layui.js"></script>
    <script type="text/javascript">

        //获取从1到10的随机整数
        var number = Math.ceil(Math.random()*10);

        layui.use('layim', function(layim){

            layim.config({
                //brief: true //是否简约模式（如果true则不显示主面板）
                voice:false,
                //初始化接口
                init: {
                    url: '/admin/chat/init/${ADMINUSERNAME}'
                    ,data: {}
                }
            });

            //建立WebSocket通讯
            //注意：如果你要兼容ie8+，建议你采用 socket.io 的版本。下面是以原生WS为例
            var socket = new WebSocket('ws://127.0.0.1:8080/chat/ws?type=admin&username=${ADMINUSERNAME}');

            //连接成功时触发
            socket.onopen = function(){
                console.log("与服务端通道建立成功");
            };

            //监听收到的消息
            socket.onmessage = function(res){
                //res为接受到的值，如 {"emit": "messageName", "data": {}}
                //emit即为发出的事件名，用于区分不同的消息

                //处理接收到的消息类型
                var obj = JSON.parse(res.data);
                var data = obj.msgData.data;

                if(0 == obj.msgType){
                    var indexuser = obj.msgData.indexuser;
                    layim.getMessage({
                        username: indexuser.alias
                        ,avatar: indexuser.avatar
                        ,id: indexuser.id
                        ,type: "friend"
                        ,content: data
                    });
                }else{
                    var indexuser = obj.msgData.indexuser;
                    layim.getMessage({
                        username: indexuser.alias
                        ,avatar: indexuser.avatar
                        ,id: indexuser.id
                        ,type: "friend"
                        ,content: data
                    });
                }
            };

            /**
             * 监听发送消息
             */
            layim.on('sendMessage', function(res){
                var mine = res.mine; //包含我发送的消息及我的信息

                var to = res.to; //对方的信息

                console.log("index user send message:"+mine.content)

                //要发送的内容
                var stringify = JSON.stringify({
                    type: 'adminMessage' //随便定义，用于在服务端区分消息类型,目前还不知道该怎么定义
                    ,data: res
                });

                socket.send(stringify);
            });

        })

</script>
</body>
</html>