<%--
  Created by Eclipse.
  User: zhaoyuan
  Date: 2020/07/08
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
    <title>question-item-add</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/css/layui.css"
          rel="external nofollow">
    <style type="text/css">

    </style>
</head>
<body>

<form class="layui-form" action="">
    <div class="layui-form-item" style="padding-top: 10px">
        <%--<div class="layui-input-block">--%>
            <input type="hidden" name="id" value="${QUESTIONITEM.id}" style="width: 50vw" autocomplete="off" class="layui-input">
        <%--</div>--%>
    </div>
    <div class="layui-form-item" style="padding-top: 10px">
        <label class="layui-form-label">问题解决方案</label>
        <div class="layui-input-block">
            <textarea style="width: 80vw" placeholder="请输入问题解决方案" lay-verify="required" class="layui-textarea" name="iName">${QUESTIONITEM.iName}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea style="width: 80vw" placeholder="请输入备注" lay-verify="required" class="layui-textarea" name="remark">${QUESTIONITEM.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <c:choose>
                <c:when test="${'01' == QUESTIONITEM.iState}">
                    <input type="checkbox" checked="" name="iState" lay-skin="switch" lay-filter="switchTest" lay-text="有效|过期">
                </c:when>
                <c:when test="${'02' == QUESTIONITEM.iState}">
                    <input type="checkbox" name="iState" lay-skin="switch" lay-filter="switchTest" lay-text="有效|过期">
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block"></br></br></br>
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" id="reset">重置</button>
            <button type="button" class="layui-btn layui-btn-danger" id="close">关闭</button>
        </div>
    </div>
</form>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/plugins/jQuery/jquery-1.8.3.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/layui.all.js"></script>
<script type="text/javascript">
    layui.use('form', function () {
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function (data) {
            layer.msg(JSON.stringify(data.field));

            //状态重新设置属性和值
            data.field.iState = $(".layui-input-block").find("input[name=iState]").val();

            $.ajax({
                url: '/admin/question/item/E',
                type: 'post',
                data: data.field,
                dataType: 'json',//预期的服务器响应的数据类型
                success: function (res) {
                    if (200 == res.status) {
                        layer.msg(res.msg, {icon: 1});
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                        window.parent.location.reload();//刷新父页面
                    } else {
                        layer.msg(res.msg, {icon: 5});
                    }

                },
                error: function (data) {
                    layer.msg("未知错误");
                }
            })

            return false;
        });

        //监听指定开关
        form.on('switch(switchTest)', function(data){
           /* layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
                offset: '6px'
            });
            layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)*/
            if(this.checked)
                $(".layui-input-block").find("input[name=iState]").val("01");
            else
                $(".layui-input-block").find("input[name=iState]").val("02");
        });

    });

    $(function () {
        close();
        reset();
    });

    /**
     * 关闭弹窗
     */
    function close() {
        $("#close").click(function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        });
    }

    /**
     * 重置
     */
    function reset() {
        $("#reset").click(function () {
            $("textarea").html("");
        });
    }
</script>
</body>
</html>