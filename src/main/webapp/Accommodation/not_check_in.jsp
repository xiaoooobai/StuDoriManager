<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2023/8/12
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<style>
    body::-webkit-scrollbar {
        display: none;
    }
</style>
<head>
    <title>未入住</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<ul class="layui-nav" lay-filter="" style="border-radius: 0px;position: relative;top:-9px">
    <li class="layui-nav-item "><a href="<%=request.getContextPath()%>/Accommodation/check_in.jsp">已入住</a></li>
    <li class="layui-nav-item layui-this"><a href="<%=request.getContextPath()%>/Accommodation/not_check_in.jsp">未入住</a></li>
</ul>

<div class="demoTable layui-form layui-row" style="margin-top: 7px">
    名字：
    <div class="layui-inline">
        <input class="layui-input" name="name" id="nameId" autocomplete="off">
    </div>
    性别：
    <div class="layui-inline">
        <select name="gender" id="genderId">
            <option value="">全部性别</option>
            <option value="男">男</option>
            <option value="女">女</option>
        </select>
    </div>
    专业：
    <div class="layui-inline">
        <input class="layui-input" name="major" id="majorId" autocomplete="off">
    </div>
    班级：
    <div class="layui-inline">
        <input class="layui-input" name="stuclass" id="stuclassId" autocomplete="off">
    </div>
    <button class="layui-btn" data-type="reload">搜索</button>
</div>
<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="checkIn">入住</a>
</script>

<script type="text/html" id="genderTemplet">
    {{#     if(d.gender=="男") {            }}
    <span class="layui-badge layui-bg-blue" style="position: relative; ">男</span>
    {{#     } else{     }}
    <span class="layui-badge layui-bg-orange" style="position: relative; ">女</span>
    {{#     }                          }}


</script>


<script>
    var $=layui.jquery;
    var layer=layui.layer;

    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#test'
            ,skin:'row'
            ,url:'<%=request.getContextPath()%>/student?method=selectNotCheckIn'
            ,title: '未入住学生数据表'
            ,cols: [[
                {field:'id', title:'ID', width:80, fixed: 'left',sort: true}
                ,{field:'name', title:'姓名', width:120 }
                ,{field:'age', title:'年龄', width:100, sort: true}
                ,{field:'major', title:'专业'}
                ,{field:'class_', title:'班级'}
                ,{field:'phone', title:'电话', width:150}
                ,{field:'gender', title:'性别', width:100,templet:'#genderTemplet'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,page: true
            ,id: 'tableId'
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            if(obj.event === 'checkIn'){
                layer.open({
                    title:'入住',
                    type: 2,
                    area: ['550px', '500px'],
                    success: function (layero, index) {    //成功获得加载changefile.html时，预先加载，将值从父窗口传到 子窗口
                        let body = layer.getChildFrame('body', index);
                        body.find("#studentId").val(data.id);
                        body.find("#nameId").val(data.name);
                        body.find("#genderId").val(data.gender);
                        body.find("#majorId").val(data.major);
                        body.find("#classId").val(data.class_);
                        layui.form.render();
                    },
                    content: '<%=request.getContextPath()%>/Accommodation/to_check_in.jsp'
                });
            }
        });
        var $ = layui.$, active = {
            reload: function(){
                //执行重载
                table.reload('tableId', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        name: $('#nameId').val(),
                        gender: $('#genderId').val(),
                        major: $('#majorId').val(),
                        class_: $('#stuclassId').val()
                    }
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    $('body').on("mouseenter",".layui-table-body tr",function () {
        $(this).siblings().find("div").css("color","#666");
        $(this).find("div").css("color","#4DA1FF");
    })
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;

        //…
    });
</script>

</body>
</html>
