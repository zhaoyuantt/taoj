<%--
  Created by IntelliJ IDEA.
  User: zhaoyuan
  Date: 2020/07/08
  Time: 11:00 am
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
	<title>question item</title>
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
	<div class="layui-row">
		<form class="layui-form layui-col-md12 x-so" id="question_item_seach">
			名称:
			<div class="layui-input-inline">
				<input type="text" name="iName" id="iName"
					   placeholder="请输入名称" autocomplete="off" class="layui-input">
			</div>
			状态:
			<div class="layui-input-inline">
				<select name="iState" id="iState">
					<option value="">---请选择---</option>
					<option value="01">有效</option>
					<option value="02">过期</option>
				</select>
			</div>
			<a id="search" class="layui-btn" lay-submit
			   lay-filter="provinceSearch"> <i class="layui-icon">&#xe615;</i>
			</a> <a id="reset" class="layui-btn"> <i class="layui-icon"></i>
		</a>
		</form>
	</div>

	<table class="layui-hide" id="test" lay-filter="test"></table>

	<script type="text/html" id="toolbarDemo">
		<div class="layui-btn-container">
			<button class="layui-btn layui-btn-sm" lay-event="goItemAddPage">添加</button>
		</div>
	</script>

	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
</div>

<script type="text/javascript">

	var qid = '${QID}';

	layui.use(['table','form'], function(){
		var form = layui.form
		var table = layui.table;

		table.render({
			elem: '#test'
			,cellMinWidth: 80 //全局定义常规单元格的最小宽度
			,url:'/admin/question/item/list'
			,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
			,where: {qid: qid}
			,cols: [[
				{type:'numbers',fixed: 'left'}
				,{field:'iName', title: '名称', fixed: 'left', edit: 'text'}
				,{field:'iState', title: '问题状态',templet:function (d) {
						if (d.iState == '01') {
							return "有效"
						} else {
							return "过期"
						}
					}}
				,{field:'created', title: '创建时间', sort: true}
				,{field:'updated', title: '更新时间', sort: true}
				,{field:'remark', title: '备注', edit: 'text'}
				,{fixed: 'right', title:'操作', toolbar: '#barDemo', fixed: 'right'}
			]]
			,page: true
		});

		$('#search').click(function() {
			var iName = $('#iName').val();
			var iState = $('#iState').val();
			table.reload('test', {
				url : '/admin/question/item/list',
				method : 'post',
				where : {
					iName : iName,
					iState : iState
				},
				page : {
					curr : 1,
					qid: qid
				}
			});
		})

		//头工具栏事件
		table.on('toolbar(test)', function(obj){
			//var checkStatus = table.checkStatus(obj.config.id);
			switch(obj.event){
				case 'goItemAddPage'://跳转到新增页面
					//iframe层-父子操作
					layer.open({
						type: 2,
						area: ['750px', '450px'],
						fixed: false, //不固定
						maxmin: true,
						content: '/page/admin/question/itemA/'+qid
					});
					break;

					//自定义头工具栏右侧图标 - 提示
				case 'LAYTABLE_TIPS':
					layer.alert('这是工具栏右侧自定义的一个图标按钮');
					break;
			};
		});

		//监听行工具事件
		table.on('tool(test)', function(obj){
			var data = obj.data;
			//console.log(obj)
			var id = data.id;
			if(obj.event === 'del'){
				layer.confirm('真的删除该条文档数据么,删除后不能恢复', function(index){
					$.ajax({
						url:'${pageContext.request.contextPath}/admin/question/item/D/'+id,
						type:'get',
						dataType:'json',
						success:function (result) {
							if(200 == result.status){
								obj.del();
								layer.msg('成功删除',{icon: 1});
							}
						}
					});

					layer.close(index);
				});
			}else if('edit' == obj.event){
				layer.open({
					type: 2,
					area: ['750px', '550px'],
					fixed: false, //不固定
					maxmin: true,
					content: '/page/admin/question/itemE/'+id
				});
			}
		});
	});

	$(function() {
		reset();
	});

	/**
	 * 清空input和select下拉框的值
	 */
	function reset() {
		$('#reset').click(function() {
			$('#iName').val("");
			$("#iState option[value='']").prop("selected", true);
			var form = layui.form
			form.render();
		})
	}

</script>
</body>
</html>