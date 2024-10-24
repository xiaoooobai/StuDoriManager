<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2023/8/10
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%--    <%@include file="../header.jsp"%>--%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css"  media="all">

    <script src="<%=request.getContextPath()%>/static/layui/layui.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/static/mylayer.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<style>
    .demo-reg-container{width: 320px; margin: 21px auto 0;}
    .demo-reg-other .layui-icon{position: relative; display: inline-block; margin: 0 2px; top: 2px; font-size: 26px;}

</style>
<form class="layui-form">
    <div class="demo-reg-container">
        <div class="layui-form-item">
            <div class="layui-input-wrap">
                <div class="layui-input-prefix">
                    <i class="layui-icon layui-icon-username"></i>
                </div><input type="text" name="username" value="" lay-verify="required" placeholder="用户名" lay-reqtext="请填写用户名" autocomplete="off" class="layui-input" >
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-wrap">
                <div class="layui-input-prefix">
                    <i class="layui-icon layui-icon-password"></i>
                </div>
                <input type="password" name="password" value="" lay-verify="required" placeholder="密码" autocomplete="off" class="layui-input" id="reg-password" lay-affix="eye">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-wrap">
                <div class="layui-input-prefix">
                    <i class="layui-icon layui-icon-password"></i>
                </div>
                <input type="password" name="confirmPassword" value="" lay-verify="required|confirmPassword" placeholder="确认密码" autocomplete="off" class="layui-input" lay-affix="eye">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-wrap">
                <div class="layui-input-prefix">
                    <i class="layui-icon layui-icon-username"></i>
                </div>
                <input type="text" name="nickname" value="" lay-verify="required" placeholder="昵称" autocomplete="off" class="layui-input" lay-affix="clear">
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo-reg">注册</button>
        </div>
    </div>
</form>
<script>
    layui.use(function(){
        var $ = layui.$;
        var form = layui.form;
        var layer = layui.layer;
        var util = layui.util;

        // 自定义验证规则
        form.verify({
            // 确认密码
            confirmPassword: function(value, item){
                var passwordValue = $('#reg-password').val();
                if(value !== passwordValue){
                    return '两次密码输入不一致';
                }
            }
        });

        // 提交事件
        form.on('submit(demo-reg)', function(data){
            var field = data.field; // 获取表单字段值

            $.post(
                '<%=request.getContextPath()%>/user?method=register',
                field,
                function(jsonObj) {
                    if (jsonObj.code == 0) {
                        mylayer.okMsg(jsonObj.msg);
                        setInterval(function() {
                            //关闭弹出框
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        }, 1500)
                    } else {
                        mylayer.errorMsg(jsonObj.msg);
                    }
                },
                'json'
            );
            return false;
        });


    });
</script>
</body>
</html>
