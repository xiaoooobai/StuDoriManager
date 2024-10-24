<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2023/8/13
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>更改宿管</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<form id="formId" class="layui-form layui-form-pane" action="">
    <input type="hidden" name="buildingId" id="buildingId"/>

    <div class="layui-form-item">
        <input type="text" id="buildingTagId" autocomplete="off"  disabled class="layui-input" style="width: 50%;float: left;text-align:center">
        <input type="text" id="managerNameId" autocomplete="off" disabled class="layui-input" style="width: 50%;float: right;text-align:center">
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">宿管</label>
        <div class="layui-input-block">
            <select name="managerId" id="managerId" lay-filter="building">
                <option value="">请选择宿管</option>
            </select>
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
    layui.use(['form'], function () {
        var form = layui.form;
        $.post(
            '<%=request.getContextPath()%>/manager?method=selectAll',
            function (result) {
                //console.log(result);
                $(result).each(function () {
                    $('#managerId').append('<option value="' + this.id + '">' + this.name + '[' + this.gender +']'+ '</option>');
                    layui.form.render('select');
                });
            },
            'json'
        );

    });
    function submitForm() {
        $.post(
            '<%=request.getContextPath()%>/buildingManager?method=change',
            $('#formId').serialize(),
            function (result) {
                if (result.code == 0) {
                    mylayer.okMsg(result.msg)
                    setInterval(function () {
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
