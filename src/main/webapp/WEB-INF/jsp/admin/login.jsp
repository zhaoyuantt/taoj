<%--
  Created by IntelliJ IDEA.
  User: zhaoyuan
  Date: 2020/2/16
  Time: 1:16 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>admin login</title>
    <link rel="shortcut icon"
          href="${pageContext.request.contextPath}/images/shortcut.ico">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/css/layui.css"
          rel="external nofollow">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/jQuery/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/layui.all.js"></script>
    <style type="text/css">
        .container {
            width: 420px;
            height: 320px;
            min-height: 320px;
            max-height: 320px;
            position: absolute;
            top: 0;
            left: 0;
            bottom: 0;
            right: 0;
            margin: auto;
            padding: 20px;
            z-index: 130;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 3px 18px rgba(100, 0, 0, .5);
            font-size: 16px;
        }

        .close {
            background-color: white;
            border: none;
            font-size: 18px;
            margin-left: 410px;
            margin-top: -10px;
        }

        .layui-input {
            border-radius: 5px;
            width: 300px;
            height: 40px;
            font-size: 15px;
        }

        .layui-form-item {
            margin-left: -20px;
        }

        #logoid {
            /* margin-top: -16px;
             padding-bottom: 15px;*/
            width: 20vw;
            height: 19vh;
            padding-left: 6em;
        }

        .layui-btn {
            margin-left: -50px;
            border-radius: 5px;
            width: 350px;
            height: 40px;
            font-size: 15px;
        }

        .verity {
            width: 120px;
        }

        .font-set {
            font-size: 13px;
            text-decoration: none;
            margin-left: 120px;
        }

        a:hover {
            text-decoration: underline;
        }

        .layui-form-mid .layui-word-aux img {

        }

    </style>
</head>
<body>
<form class="layui-form" action="" method="post">
    <div class="container">
        <%--<button class="close" title="关闭">X</button>--%>
        <div class="layui-form-mid layui-word-aux">
            <img id="logoid" src="${pageContext.request.contextPath}/images/login_logo.jpg" alt="login_logo">
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input type="text" name="username" required lay-verify="required|username" placeholder="请输入用户名"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密 &nbsp;&nbsp;码</label>
            <div class="layui-input-inline">
                <input type="password" name="password" required lay-verify="required|pass" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
            <!-- <div class="layui-form-mid layui-word-aux">辅助文字</div> -->
        </div>

        <%--<div class="layui-form-item">
            <label class="layui-form-label">记住密码</label>
            <div class="layui-input-block">
                <input type="checkbox" name="close" lay-skin="switch" lay-text="ON|OFF">
            </div>
        </div>--%>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">登录</button>
            </div>
        </div>

        <%-- <a href="" class="font-set">忘记密码?</a> <a href="" class="font-set">立即注册</a>--%>
    </div>
</form>
<script type="text/javascript">

    var redirect = "${REDIRCT}";

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form

        //监听提交
        form.on('submit(formDemo)', function (data) {

            /*console.info(data.field);
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            });*/

            var username = data.field.username;
            var password = data.field.password;

            $.ajax({
                url: '/admin/user/login/S',
                type: 'post',
                data: {username: username, passwd: password, redirect: redirect},
                dataType: 'json',//预期的服务器响应的数据类型
                success: function (res) {
                    var redirectUrl = res.data;
                    if (200 == res.status) {
                        layer.msg("登录成功,你好，你的动作将被审计，请严格遵守平台使用规范");
                        location.href = redirectUrl;
                    } else {
                        layer.msg(res.msg);
                    }

                },
                error: function () {
                    layer.msg("未知错误");
                }
            });

            return false;
        });

        //校验规则
        form.verify({
            username: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    return '用户名不能有特殊字符';
                }
                if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                    return '用户名首尾不能出现下划线\'_\'';
                }
                if (/^\d+\d+\d$/.test(value)) {
                    return '用户名不能全为数字';
                }
            }
            , pass: [
                /^[\S]{6,12}$/
                , '密码必须6到12位，且不能出现空格'
            ]
        });

    });

</script>
</body>
</html>
