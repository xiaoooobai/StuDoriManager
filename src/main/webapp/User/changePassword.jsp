<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2023/8/13
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>修改密码</title>
  <%@ include file="../header.jsp"%>
</head>
<body>
<style>
  .demo-reg-container{width: 320px; margin: 21px auto 0;}
  .demo-reg-other .layui-icon{position: relative; display: inline-block; margin: 0 2px; top: 2px; font-size: 26px;}

</style>
<form class="layui-form">
  <input type="hidden" name="userId" id="userId"/>
  <div class="demo-reg-container">
    <div class="layui-form-item">
      <div class="layui-input-wrap">
        <div class="layui-input-prefix">
          <i class="layui-icon layui-icon-password"></i>
        </div><input type="password" name="oldPassword" value="" lay-verify="required" placeholder="原密码" lay-reqtext="请填写原密码" autocomplete="off" class="layui-input" lay-affix="eye">
      </div>
    </div>
    <div class="layui-form-item">
      <div class="layui-input-wrap">
        <div class="layui-input-prefix">
          <i class="layui-icon layui-icon-password"></i>
        </div>
        <input type="password" name="newPassword" value="" lay-verify="required" placeholder="新密码" autocomplete="off" class="layui-input" id="reg-password" lay-affix="eye">
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
      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo-change">立即修改</button>
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
    form.on('submit(demo-change)', function(data){
      var field = data.field; // 获取表单字段值

      $.post(
              '<%=request.getContextPath()%>/user?method=changePassword',
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
