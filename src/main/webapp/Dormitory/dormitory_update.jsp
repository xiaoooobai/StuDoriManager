
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>更新</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<form id="formId" class="layui-form layui-form-pane" action="">
    <input type="hidden" name="id" value="${dormitory.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">宿舍号</label>
        <div class="layui-input-block">
            <input type="text" name="tag" value="${dormitory.tag}" autocomplete="off" placeholder="请输入宿舍号" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">容纳人数</label>
        <div class="layui-input-block">
            <input type="number" name="contain"  value="${dormitory.contain}" autocomplete="off" placeholder="请输入可容纳人数" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">补充信息</label>
        <div class="layui-input-block">
            <input type="text" name="note" value="${dormitory.note}" autocomplete="off" placeholder="请输入补充信息" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属公寓id</label>
        <div class="layui-input-block">
<%--            <input type="text" name="buildingId" value="${dormitory.buildingId}" autocomplete="off" placeholder="请输入所属公寓id" class="layui-input">--%>
                <select name="buildingId" id="buildingId">
                    <option value="${dormitory.buildingId}">[现在]${dormitory.buildingTag}--${dormitory.buildingGender}(${dormitory.buildingNote})</option>
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
            '<%=request.getContextPath()%>/building?method=selectAll',
            function (result) {
                //console.log(result);
                $(result).each(function () {
                    $('#buildingId').append('<option value="' + this.id + '">' + this.tag + '--' + this.gender + '(' + this.note + ')' + '</option>');
                    layui.form.render('select');
                });
            },
            'json'
        );
    });

    function submitForm() {
        $.post(
            '<%=request.getContextPath()%>/dormitory?method=update',
            $('#formId').serialize(),
            function(result) {
                //console.log(result);
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
