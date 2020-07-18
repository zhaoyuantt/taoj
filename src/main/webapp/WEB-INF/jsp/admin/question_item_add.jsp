<%--
  Created by Eclipse.
  User: zhaoyuan
  Date: 2020/07/05
  Time: 5:11 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
            <input type="hidden" name="qId" value="${QID}" style="width: 50vw" autocomplete="off" class="layui-input">
        <%--</div>--%>
    </div>
    <div class="layui-form-item" style="padding-top: 10px">
        <label class="layui-form-label">问题解决方案</label>
        <div class="layui-input-block">
            <textarea style="width: 80vw" placeholder="请输入问题解决方案" lay-verify="required" class="layui-textarea" name="iName"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea style="width: 80vw" placeholder="请输入备注" lay-verify="required" class="layui-textarea" name="remark"></textarea>
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

            $.ajax({
                url: '/admin/question/item/A',
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

    });

    $(function () {
        close();
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

</script>
</body>
</html>