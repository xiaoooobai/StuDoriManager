<%@ page import="com.syz.dormitory.pojo.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<style>
	body::-webkit-scrollbar {
		display: none;
	}
</style>
<head>
	<title>学生宿舍管理系统</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/layui/css/layui.css"/>
	<script src="<%=request.getContextPath()%>/static/layui/layui.js" type="text/javascript" charset="utf-8"></script>
</head>
<body class="layui-layout-body">
<input type="hidden" name="userId" id="userId" value="${user.id}"/>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">学生宿舍管理系统</div>
			<ul class="layui-nav layui-layout-left">   </ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item">
				    <a href="javascript:">
<%--				        	用户${user.nickname}--%>
	<%
		User user= (User) request.getSession().getAttribute("user");
		String username=user.getUsername();
		if(username.equals("admin")){
	%>
	<span class="layui-badge layui-bg-red" style="position: relative;">${user.nickname}</span>
<%
	}else{
%>
	<span class="layui-badge layui-bg-green" style="position: relative; ">${user.nickname}</span>
<%
	}
%>
				    </a>
					<dl class="layui-nav-child">
						<dd>
							<a id="changePasswordId">修改密码</a>
						</dd>
					</dl>
				</li>
				<li class="layui-nav-item">
				    <a href="javascript:logout()">注销</a>
				</li>
			</ul>
		</div>
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" >
					<li class="layui-nav-item layui-nav-itemed">
					    <a href="javascript:">信息维护</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:"
								   data-url="<%=request.getContextPath()%>/Student/student_list.jsp"
								   class="site-demo-active">学生信息管理</a>
							</dd>
							<dd>
								<a href="javascript:"
								   data-url="<%=request.getContextPath()%>/Manager/manager_list.jsp"
								   class="site-demo-active">宿管信息管理</a>
							</dd>
							<dd>
								<a href="javascript:"
								data-url="<%=request.getContextPath()%>/Building/building_list.jsp"
								class="site-demo-active">公寓楼信息管理</a>
							</dd>
							<dd>
								<a href="javascript:"
								data-url="<%=request.getContextPath()%>/Dormitory/dormitory_list.jsp"
								class="site-demo-active">宿舍信息管理</a>
							</dd>
							<dd>
								<a href="javascript:"
								   data-url="<%=request.getContextPath()%>/Accommodation/check_in.jsp"
								   class="site-demo-active">学生住宿管理</a>
							</dd>
							<dd>
								<a href="javascript:"
								   data-url="<%=request.getContextPath()%>/Echarts/echarts.jsp"
								   class="site-demo-active">统计图表</a>
							</dd>

						</dl>
					</li>

<%
	if (username.equals("admin")) {
%>
					<li class="layui-nav-item">
					    <a href="javascript:">超级权限</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:"
								class="site-demo-active" 
								data-url="<%=request.getContextPath()%>/User/user_list.jsp">
								系统用户管理</a>
							</dd>

						</dl>
					</li>

<%
	}
%>
				</ul>
			</div>
		</div>
		<div class="layui-body" style="padding-bottom: 0px;"><!-- 内容主体区域 -->
			<iframe name="rightframe" width="100%" height="100%" style="    border-width: 0px;" src="<%=request.getContextPath()%>/Echarts/echarts.jsp"></iframe>
		</div>

	</div>
	<script type="text/javascript">
		var $ = layui.jquery;
		var layer = layui.layer;
		var element = layui.element;
		$('.site-demo-active').click(function() {
			window.open($(this).data('url'), "rightframe");
		});
		element.render();// element.init();
		function openURL(url){
			window.open(url, "rightframe");
		}

		function logout() {
			layer.confirm(
					'您确认要退出么',
					{icon:3},
					function() {
						location.href = '<%=request.getContextPath()%>/user?method=logout'
					}
			);
		}
		var tc = document.getElementById("changePasswordId");
		tc.onmouseover = function (){
			var body = document.querySelector("body")
			body.style.cursor= "pointer"
		}
		tc.onclick=function (){
			var userId=document.getElementById("userId").value;
			layer.open({
				type: 2,
				title:"修改密码",
				success: function (layero, index) {    //成功获得加载changefile.html时，预先加载，将值从父窗口传到 子窗口
					let body = layer.getChildFrame('body', index);
					body.find("#userId").val(userId);
					layui.form.render();
				},
				content: '<%=request.getContextPath()%>/User/changePassword.jsp',
				area: ['400px', '66%']
			});
		}
	</script>

</body>
</html>