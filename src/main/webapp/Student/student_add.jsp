
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>添加</title>
    <%@ include file="../header.jsp"%>
</head>
<body>

<form id="formId" class="layui-form layui-form-pane" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <input type="text" name="name" autocomplete="off" placeholder="请输入名字" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年龄</label>
        <div class="layui-input-block">
            <input type="number" name="age" autocomplete="off" placeholder="请输入年龄" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">专业</label>
        <div class="layui-input-block">
            <input type="text" name="major" autocomplete="off" placeholder="请输入专业" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">班级</label>
        <div class="layui-input-block">
            <input type="text" name="class_" autocomplete="off" placeholder="请输入班级" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">电话</label>
        <div class="layui-input-block">
            <input type="text" name="phone" autocomplete="off" placeholder="请输入手机号" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <input type="radio" name="gender" value="男" title="男" checked="">
            <input type="radio" name="gender" value="女" title="女">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" onclick="submitForm()" class="layui-btn" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>


<script>
    function submitForm() {
        $.post(
            '<%=request.getContextPath()%>/student?method=add',
            $('#formId').serialize(),
            function(result) {
                if (result.code == 0) {
                    mylayer.okMsg(result.msg)
                    setInterval(function() {
                        //关闭弹出框
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        //刷新父页面
                        window.parent.location.reload();
                    }, 1500)
                } else {
                    mylayer.errorMsg(result.msg);
                }
            },
            'json'
        );
    }
</script>
</body>
</html>
