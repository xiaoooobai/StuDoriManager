<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2023/8/12
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>转宿</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<form id="formId" class="layui-form layui-form-pane" action="">
    <input type="hidden" name="stuId" id="studentId"/>
    <div class="layui-form-item">
            <input type="text" id="nameId" autocomplete="off"  disabled class="layui-input" style="width: 50%;float: left;text-align:center">
            <input type="text" id="genderId" autocomplete="off" disabled class="layui-input" style="width: 50%;float: right;text-align:center">
    </div>
    <div class="layui-form-item">
        <input type="text" id="majorId" autocomplete="off"  disabled class="layui-input" style="width: 50%;float: left;text-align:center">
        <input type="text" id="classId" autocomplete="off" disabled class="layui-input" style="width: 50%;float: right;text-align:center">
    </div>
    <div class="layui-form-item">
        <input type="text" id="nowBuildingId" autocomplete="off"  disabled class="layui-input" style="width: 50%;float: left;text-align:center">
        <input type="text" id="nowDorId" autocomplete="off" disabled class="layui-input" style="width: 50%;float: right;text-align:center">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">公寓</label>
        <div class="layui-input-block">
            <select name="buildingId" id="buildingId" lay-filter="building">
                <option value="">请选择公寓</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">宿舍</label>
        <div class="layui-input-block">
            <select name="dorId" id="dormitoryId">
                <option value="">请选择宿舍</option>
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
                    $('#buildingId').append('<option value="' + this.id + '">' + this.tag + '--' + this.gender + '(' + this.note +')'+ '</option>');
                    layui.form.render('select');
                });
            },
            'json'
        );

        form.on('select(building)', function(data){
            var buildingId=data.value;
            $('#dormitoryId').empty();
            $('#dormitoryId').append('<option value="">请选择宿舍</option>');
            if(buildingId==null||buildingId==''){
                layui.form.render('select');
                return;
            }
            //console.log(buildingId);
            $.post(
                '<%=request.getContextPath()%>/dormitory?method=selectByBuilding',
                {'buildingId': buildingId},
                function (jsonObj) {
                    //console.log(jsonObj)
                    $(jsonObj).each(function () {
                        $('#dormitoryId').append('<option value="' + this.id + '">' + this.tag + '--' + this.contain + '(' + this.note +')'+ '</option>');
                        layui.form.render('select');
                    });
                },
                'json'
            );
        });

    });
    function submitForm() {
        $.post(
            '<%=request.getContextPath()%>/stuDor?method=change',
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
