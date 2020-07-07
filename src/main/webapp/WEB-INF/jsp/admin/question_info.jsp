<%--
  Created by IntelliJ IDEA.
  User: zhaoyuan
  Date: 2020/07/04
  Time: 23:27 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
		  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>file upload</title>
	<link rel="shortcut icon"
		  href="${pageContext.request.contextPath}/images/shortcut.ico">
	<link rel="stylesheet"
		  href="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/css/layui.css"
		  rel="external nofollow">
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/plugins/jQuery/jquery-1.8.3.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/layui.all.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/plugins/layui-v2.5.6/layui/layui.js"></script>
	<style type="text/css">

	</style>
</head>
<body>
<div class="main">
	<table class="layui-hide" id="test" lay-filter="test"></table>

	<script type="text/html" id="toolbarDemo">
		<div class="layui-btn-container">
			<button class="layui-btn layui-btn-sm" lay-event="goAddPage">添加</button>
		</div>
	</script>

	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
</div>

<script type="text/javascript">
	layui.use(['table','form'], function(){
		var form = layui.form
		var table = layui.table;

		table.render({
			elem: '#test'
			,cellMinWidth: 80 //全局定义常规单元格的最小宽度
			,url:'/admin/question/list'
			,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
			,cols: [[
				{type:'numbers',fixed: 'left'}
				,{field:'name', title: '问题名称', fixed: 'left'}
				,{field:'state', title: '问题状态',templet:function (d) {
						var qId = d.id;
						if (d.state == '01') {
							return "<input type='checkbox'  qId = '" + qId + "' lay-filter='state' lay-skin='switch' lay-text='ON|OFF' checked>"
						} else {
							return "<input type='checkbox'  qId = '" + qId + "' lay-filter='state' lay-skin='switch' lay-text='ON|OFF'>"
						}
					}}
				,{field:'created', title: '创建时间', sort: true}
				,{field:'updated', title: '更新时间', sort: true}
				,{field:'remark', title: '备注'}
				,{fixed: 'right', title:'操作', toolbar: '#barDemo', fixed: 'right'}
			]]
			,page: true
		});

		//头工具栏事件
		table.on('toolbar(test)', function(obj){
			//var checkStatus = table.checkStatus(obj.config.id);
			switch(obj.event){
				case 'goAddPage'://跳转到新增页面
					//iframe层-父子操作
					layer.open({
						type: 2,
						area: ['700px', '450px'],
						fixed: false, //不固定
						maxmin: true,
						content: '/page/admin/questionA'
					});
					break;

					//自定义头工具栏右侧图标 - 提示
				case 'LAYTABLE_TIPS':
					layer.alert('这是工具栏右侧自定义的一个图标按钮');
					break;
			};
		});

		//监听指定开关
		form.on('switch(state)', function(data){
			layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
				offset: '6px'
			});

			var qid = data.elem.attributes['qid'].nodeValue;
			var pstate = "";
			if(true == this.checked)
				pstate = "01";
			else
				pstate = "02";

			//console.log(data);
			//layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
			$.ajax({
				url : '/admin/question/U/state/' + qid + "/" + pstate,
				type : 'get',
				dataType : 'json',//预期的服务器响应的数据类型
				success : function(res) {
					if (200 == res.status) {
						layer.alert('ok', {icon: 1});
						location.reload();
					} else {
						layer.msg(res.msg, {icon: 5});
					}
				},
				error : function (){
					layer.msg('未知错误', {icon: 5});
				}
			})
		});

		//监听行工具事件
		table.on('tool(test)', function(obj){
			var data = obj.data;
			//console.log(obj)
			var historyId = data.id;
			var category = data.catagory;
			if(obj.event === 'del'){
				layer.confirm('真的删除行么,确认将删除该文件所有的导入文档记录!!!', function(index){
					$.ajax({
						url:'${pageContext.request.contextPath}/file/history/delete/'+historyId+"/"+category,
						type:'get',
						dataType:'json',
						success:function (result) {
							if(200 == result.status){
								obj.del();
								layer.msg('成功删除 '+result.data+" 条文档记录");
							}
						}
					});

					layer.close(index);
				});
			}
		});
	});

	/*function xijiaEventSwitch() {
		form.on('switch(state)', function (data) {
			console.log(data.elem.attributes['menuId'].nodeValue);
			console.log(data.elem.checked); // 开关是否开启，true或者false
			console.log(data.value);        // 开关value值，也可以通过data.elem.value得到

			// let menuId = data.elem.attributes['menuId'].nodeValue;
			//console.log(data.elem);         // 得到checkbox原始DOM对象
			//console.log(data.othis);        // 得到美化后的DOM对象
		});
	}*/

</script>
</body>
</html>