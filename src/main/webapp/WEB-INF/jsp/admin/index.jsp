<%--
  Created by IntelliJ IDEA.
  User: zhaoyuan
  Date: 2020/07/07
  Time: 4:13 pm
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
	<title>Intelligent customer service answering system</title>
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
		body .mySkin .layui-layer-content {
			width: 420px;
			height: 240px;
			background-color: #81BA25;
			box-shadow: none;
			color: #fff;
			padding: 20px;
		}
	</style>
</head>
<body>
<div class="layui-layout-admin">
	<!--头部-->
	<div class="layui-header">
		<div class="layui-logo">intelligent customer</div>
		<ul class="layui-nav layui-layout-left">
		</ul>
		<ul class="layui-nav layui-layout-right">
			<li class="layui-nav-item">
				<a href="">
					<img src="${pageContext.request.contextPath}/images/avatar.jpg" class="layui-nav-img">
					${TUSERDETAIL.alies}
				</a>
				<dl class="layui-nav-child">
					<dd>
						<a href="javascript:logout()">安全退出</a>
					</dd>
				</dl>
			</li>
		</ul>
	</div>

	<!--左侧-->
	<div class="layui-side layui-bg-black">
		<div class="layui-side-scroll">
			<ul class="layui-nav layui-nav-tree" lay-filter="hbkNavbar">
				<li class="layui-nav-item">
					<a href="javascript:;" rel="external nofollow">基本元素</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:;" rel="external nofollow" class="site-demo-active" data-id="1"
							   data-title="问题" data-url="/page/admin/question">问题</a>
						</dd>
					</dl>
				</li>
			</ul>
			<ul class="layui-nav layui-nav-tree" lay-filter="hbkNavbar">
				<li class="layui-nav-item">
					<a href="javascript:;" rel="external nofollow">客服</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:;" rel="external nofollow" class="site-demo-active" data-id="1"
							   data-title="问题" data-url="/page/admin/chat/${TUSERDETAIL.username}">回答</a>
						</dd>
					</dl>
				</li>
			</ul>
		</div>
	</div>

	<!--中间主体-->
	<div class="layui-body" id="container">
		<div class="layui-tab" lay-filter="demo" lay-allowClose="true">
			<ul class="layui-tab-title">
				<li class="layui-this">首页</li>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<h2>welcome to Intelligent customer service answering system</h2>
					<h2>LayIM目前并非开源产品，因此如果你是通过官网捐赠渠道获得LayIM，你将成为LayIM的终身会员，并可以将LayIM应用在任意正规平台。如果你通过非捐赠渠道获得LayIM，我们并不会进行过多追究，但是请勿对 LayIM 本身二次出售。版权最终解释权为：layui.com 所有</h2>
				</div>
			</div>
		</div>
	</div>

	<!--底部-->
	<div class="layui-footer">
		<center>landasoft org 版权所有©</center>
	</div>
</div>

<script type="text/javascript">

	/**
	 * 点击右侧菜单，显示中间主体
	 */
	/*layui.use('element', function () {
        var element = layui.element;
        $('.layui-nav-child a').click(function () {
            var options = eval('(' + $(this).data('options') + ')');
            var url = options.url;
            var title = options.title;
            element.tabAdd('tabs',
                {
                    title: title,
                    content: '<iframe scrolling="auto" frameborder="0" src="'
                    + url
                    + '" style="width:100%;height:80vh;"></iframe>'
                });
        });
    });*/
	layui.use(['element', 'layer', 'jquery'], function () {
		var element = layui.element;
		// var layer = layui.layer;
		var $ = layui.$;
		// 配置tab实践在下面无法获取到菜单元素
		$('.site-demo-active').on('click', function () {
			var dataid = $(this);
			//这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
			if ($(".layui-tab-title li[lay-id]").length <= 0) {
				//如果比零小，则直接打开新的tab项
				active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
			} else {
				//否则判断该tab项是否以及存在
				var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
				$.each($(".layui-tab-title li[lay-id]"), function () {
					//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
					if ($(this).attr("lay-id") == dataid.attr("data-id")) {
						isData = true;
					}
				})
				if (isData == false) {
					//标志为false 新增一个tab项
					active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
				}
			}
			//最后不管是否新增tab，最后都转到要打开的选项页面上
			active.tabChange(dataid.attr("data-id"));
		});

		var active = {
			//在这里给active绑定几项事件，后面可通过active调用这些事件
			tabAdd: function (url, id, name) {
				//新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
				//关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
				element.tabAdd('demo', {
					title: name,
					content: '<iframe data-frameid="' + id + '" scrolling="auto" frameborder="0" src="' + url + '" style="width:100%;height:99%;"></iframe>',
					id: id //规定好的id
				})
				FrameWH();  //计算ifram层的大小
			},
			tabChange: function (id) {
				//切换到指定Tab项
				element.tabChange('demo', id); //根据传入的id传入到指定的tab项
			},
			tabDelete: function (id) {
				element.tabDelete("demo", id);//删除
			}
		};

		function FrameWH() {
			var h = $(window).height()-100;
			$("iframe").css("height", h + "px");
		}
	});

	/**
	 * 安全退出
	 */
	function logout() {
		$.ajax({
			url:'/admin/user/logout',
			type:'get',
			dataType:'json',
			success:function (result) {
				if(200 == result.status){
					layer.msg("logout success");
					location.href = result.data
				}else{
					layer.msg("未知错误");
				}
			},
			error:function () {
				layer.msg("未知错误");
			}
		});
	}

</script>
</body>
</html>