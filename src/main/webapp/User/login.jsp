<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>

<html>
<head>
    <title>登录</title>
    <%@ include file="../header.jsp"%>
</head>
<body>

<style>
    .demo-login-container{width: 320px; margin: 21px auto 0;}
    .demo-login-other .layui-icon{position: relative; display: inline-block; margin: 0 2px; top: 2px; font-size: 26px;}

    #bg-container{background-color: #16baaa;width:100%;height: 100%;position: relative}
    #inner-container{background-color: white; position: absolute;top:17%;right: 12%;height: 66%;width: 360px;
                    border-radius: 0px 12px 12px 0px ;border:solid #16baaa;box-shadow: 4px 4px 2px 1px rgba(0, 0, 0, 0.2);
    }
    #login-title{text-align:center;position: absolute;left: 10%;right: 10%;
                    top:8%;height:10%;font-size: 200%;color: #16baaa;
    }
    #login-container{background-color: white;position: absolute;top: 20%;left: 2%;right: 2%;

    }
    #login-left{background-color: #8a6d3b;position: absolute;top: 17%;right:calc(12% + 360px);width: 420px;
                    height: 66%;border-radius: 12px 0px 0px 12px ;border:solid #16baaa;box-shadow: 4px 4px 2px 1px rgba(0, 0, 0, 0.2);
    }
</style>

<div id="bg-container">
    <div id="login-left">
        <img style="width: 100%;height: 100%; object-fit: cover;border-radius: 12px;" src="<%=request.getContextPath()%>/static/images/bg.png" alt="..">
    </div>
    <div id="inner-container">
        <div id="login-title">学生宿舍管理系统</div>
        <div id="login-container">
            <form class="layui-form">
                <div class="demo-login-container">
                    <div class="layui-form-item">
                        <div class="layui-input-wrap">
                            <div class="layui-input-prefix">
                                <i class="layui-icon layui-icon-username"></i>
                            </div>
                            <input type="text" name="username" value="" lay-verify="required" placeholder="用户名" lay-reqtext="请填写用户名" autocomplete="off" class="layui-input" lay-affix="clear">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-wrap">
                            <div class="layui-input-prefix">
                                <i class="layui-icon layui-icon-password"></i>
                            </div>
                            <input type="password" name="password" value="" lay-verify="required" placeholder="密   码" lay-reqtext="请填写密码" autocomplete="off" class="layui-input" lay-affix="eye">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-row">
                            <div class="layui-col-xs7">
                                <div class="layui-input-wrap">
                                    <div class="layui-input-prefix">
                                        <i class="layui-icon layui-icon-vercode"></i>
                                    </div>
                                    <input type="text" name="code" value="" lay-verify="required" placeholder="验证码" lay-reqtext="请填写验证码" autocomplete="off" class="layui-input" lay-affix="clear">
                                </div>
                            </div>
                            <div class="layui-col-xs5">
                                <div style="margin-left: 10px;">
                                    <img src="<%=request.getContextPath()%>/verifyCode" onclick="this.src='<%=request.getContextPath()%>/verifyCode?t='+ new Date().getTime();">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
                    </div>
                    <div class="layui-form-item">
                        <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo-login">登录</button>
                    </div>
                    <div class="layui-form-item demo-login-other" >
                        <a id="register-id">注册帐号</a></span>
                    </div>
                </div>
            </form>
        </div>
    </div>


</div>





<script>
    layui.use(function(){
        var form = layui.form;
        var layer = layui.layer;
        // 提交事件
        form.on('submit(demo-login)', function(data){
            var field = data.field;
            $.post(
                '<%=request.getContextPath()%>/user?method=login',
                field,
                function(jsonObj) {
                    if (jsonObj.code == 0) {
                        mylayer.okUrl(jsonObj.msg, '<%=request.getContextPath()%>');
                    } else {
                        mylayer.errorMsg(jsonObj.msg);
                    }
                },
                'json'
            );
            return false; // 阻止默认 form 跳转
        });
    });

    //注册事件
    var rg = document.getElementById("register-id");
    rg.onmouseover = function (){
        var body = document.querySelector("body")
        body.style.cursor= "pointer"
    }
    rg.onclick=function (){
        layer.open({
            type: 2,
            title:"注册",
            content: 'register.jsp',
            area: ['400px', '66%']
        });
    }

</script>

</body>
</html>
