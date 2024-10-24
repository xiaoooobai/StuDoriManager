
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
    <input type="hidden" name="id" value="${building.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">楼号</label>
        <div class="layui-input-block">
            <input type="text" name="tag" value="${building.tag}" autocomplete="off" placeholder="请输入楼号" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">最大房间数</label>
        <div class="layui-input-block">
            <input type="number" name="roomsMax"  value="${building.roomsMax}" autocomplete="off" placeholder="请输入最大房间数" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">补充信息</label>
        <div class="layui-input-block">
            <input type="text" name="note" value="${building.note}" autocomplete="off" placeholder="请输入补充信息" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <c:if test="${building.gender=='男'}">
                <input type="radio" name="gender" value="男" title="男" checked>
                <input type="radio" name="gender" value="女" title="女">
            </c:if>
            <c:if test="${building.gender=='女'}">
                <input type="radio" name="gender" value="男" title="男">
                <input type="radio" name="gender" value="女" title="女" checked>
            </c:if>
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
            '<%=request.getContextPath()%>/building?method=update',
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
