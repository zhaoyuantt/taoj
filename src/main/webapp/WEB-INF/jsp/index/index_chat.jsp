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
    <title>${INDEXUSER.alias}-chat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/layui.layim-v3.0.1/layui/css/layui.css" media="all">
    <style type="text/css">
        .user_container{
            float: right;
            font-size: medium;
        }
    </style>
</head>
<body>
    <div class="user_container">
        你好，${INDEXUSER.alias}
    </div>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/jQuery/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/layui.layim-v3.0.1/layui/layui.js"></script>
    <script type="text/javascript">

        //获取从1到10的随机整数
        var number = Math.ceil(Math.random()*10);

        layui.use('layim', function(layim){
            //先来个客服模式压压精
            layim.config({
                brief: true //是否简约模式（如果true则不显示主面板）
                ,voice:false
                ,init: {//********************现在使用模拟数据，后期改为接口获得数据
                    mine: {
                        "username": "${INDEXUSER.alias}" //我的昵称
                        ,"id": "${INDEXUSER.id}" //我的ID
                        ,"status": "online" //在线状态 online：在线、hide：隐身
                        ,"remark": "${INDEXUSER.sign}" //我的签名
                        ,avatar: "${INDEXUSER.avatar}"
                    }
                    ,friend: []
                    ,group: []
                }
            }).chat({//服务端信息
                name: 'taoj'
                ,type: 'friend'
                ,avatar: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3686140125,1588558660&fm=26&gp=0.jpg'
                ,id: -2
            });

            //建立WebSocket通讯
            //注意：如果你要兼容ie8+，建议你采用 socket.io 的版本。下面是以原生WS为例
            var socket = new WebSocket('ws://127.0.0.1:8080/chat/ws?type=index&username=${INDEXUSER.username}');

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

                console.log("接收到服务端发送的消息:"+obj.msgData.data);

                if(0 == obj.msgType){
                    //系统消息
                    layim.getMessage({
                        username: 'taoj'
                        ,avatar: "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3686140125,1588558660&fm=26&gp=0.jpg"
                        ,id: "-2"
                        ,system: true
                        ,type: "friend"
                        ,content: obj.msgData.data
                    });
                } else if(1 == obj.msgType){
                    //自动消息
                    layim.getMessage({
                        username: 'taoj'
                        ,avatar: "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3686140125,1588558660&fm=26&gp=0.jpg"
                        ,id: "-2"
                        ,type: "friend"
                        ,content: obj.msgData.data
                    });

                    //转义，不知道为什么<会被认为是小于号,>会被认为是大于号
                    var $layuiMessage = $("div.layim-chat-text:last");
                    var layuiMessageHtml = $layuiMessage.html();
                    layuiMessageHtml=layuiMessageHtml.replace(/&lt;/g,"<");//替换里面所有的转义
                    layuiMessageHtml=layuiMessageHtml.replace(/&nbsp;/g," ");
                    layuiMessageHtml=layuiMessageHtml.replace(/&gt;/g,">");
                    $layuiMessage.html(layuiMessageHtml);
                } else{
                    layim.getMessage({
                        username: 'taoj'
                        ,avatar: "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3686140125,1588558660&fm=26&gp=0.jpg"
                        ,id: "-2"
                        ,type: "friend"
                        ,content: obj.msgData.data
                    });
                }
            };

            //另外还有onclose、onerror，分别是在链接关闭和出错时触发。
            socket.onclose= function(res){

            }

            //基本上常用的就上面几个了，是不是非一般的简单？


            /**
             * 监听发送消息
             */
            layim.on('sendMessage', function(res){
                var mine = res.mine; //包含我发送的消息及我的信息

                var to = res.to; //对方的信息

                console.log("index user send message:"+mine.content)

                //要发送的内容
                var stringify = JSON.stringify({
                    type: 'indexMessage' //随便定义，用于在服务端区分消息类型,目前还不知道该怎么定义
                    ,data: res
                });

                socket.send(stringify);
            });

        })

        /**
         * 预定义问题点击事件触发方法
         */
        function showMsgLayuiImMsgTextArea(qid) {

            //jQuery对象
            var $questionItem = $("#"+qid);
            var questionItemHtml = $questionItem.html();

            //只保留中文
            var questionItemHtmlWithReplace = questionItemHtml.replace(/[^\u4E00-\u9FA5]/g,'');

            //在layui im 消息编辑框显示
            $("textarea").val(questionItemHtmlWithReplace);
            //自定触发发送按钮点击事件
            $('.layim-send-btn').trigger("click");

        }

</script>
</body>
</html>