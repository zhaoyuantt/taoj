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
    <title>question-add</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/css/layui.css"
          rel="external nofollow">
    <style type="text/css">
    </style>
</head>
<body>

<form class="layui-form" action="">
    <div class="layui-form-item" style="padding-top: 10px">
        <label class="layui-form-label">问题名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="title"
                   style="width: 50vw" autocomplete="off" placeholder="请输入问题名称"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入备注" class="layui-textarea" name="remark"></textarea>
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
                url: '/admin/question/A',
                type: 'post',
                data: data.field,
                dataType: 'json',//预期的服务器响应的数据类型
                success: function (res) {
                    if (200 == res.status) {
                        /*layer.open({
                            type: 0
                            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                            //,id: 'layerDemo'+type //防止重复弹出
                            , content: '<div style="padding: 20px 100px;">' + '保存成功' + '</div>'
                            , btn: '关闭'
                            , btnAlign: 'c' //按钮居中
                            , shade: 0 //不显示遮罩
                            , yes: function () {
                                layer.closeAll();
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                                window.parent.location.reload();//刷新父页面
                            }
                        });*/
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

        //自定义验证规则
        form.verify({
            title: function (value) {
                if (value.length < 1) {
                    return '标题不得为空哦，么么哒';
                }
            }
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
            $('input').val("");
            $('textarea').val("");
        });
    }
</script>
</body>
</html>