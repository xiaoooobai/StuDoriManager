
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<style>
    body::-webkit-scrollbar {
        display: none;
    }
</style>
<head>
    <title>宿管列表</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
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
    <button class="layui-btn" data-type="reload">搜索</button>
</div>
<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
        <button class="layui-btn layui-btn-sm" lay-event="deleteAll">批量删除</button>
    </div>
</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="genderTemplet">
    {{#     if(d.gender=="男") {            }}
    <span class="layui-badge layui-bg-blue" style="position: relative; ">男</span>
    {{#     } else{     }}
    <span class="layui-badge layui-bg-orange" style="position: relative; ">女</span>
    {{#     }                          }}
</script>
<script type="text/html" id="imageTemplet">
    <img src="/pic/{{d.image==''?'null.jpg':d.image}}" style="height: 100%; object-fit: cover;"/>
</script>

<script>
    var $=layui.jquery;
    var layer=layui.layer;
    function deleteById(id) {
        <%--location.href = '${pageContext.request.contextPath}/user?method=deleteById&id=' + id;--%>

        $.post(
            '<%=request.getContextPath()%>/manager?method=deleteById',
            {'id':id},
            function (result){
                if(result.code==0){
                    setTimeout('window.location.reload()', 1);
                }else{
                    mylayer.errorMsg("删除失败");
                }
            },
            'json'
        );
    }
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#test'
            ,skin:'row'
            ,url:'<%=request.getContextPath()%>/manager?method=selectByPage'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', ]
            ,title: '宿管数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID', width:80, fixed: 'left',sort: true}
                ,{field:'name', title:'姓名' }
                ,{field:'gender', title:'性别', width:100,templet:'#genderTemplet'}
                ,{field:'image', title:'头像',templet: "#imageTemplet"}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,page: true
            ,id: 'tableId'
        });

        //添加事件
        table.on('toolbar(test)',function (obj){
            var checkStatus = table.checkStatus(obj.config.id);
            if(obj.event==='add'){
                layer.open({
                    title:'添加',
                    type: 2,
                    area: ['550px', '500px'],
                    content: '<%=request.getContextPath()%>/Manager/manager_add.jsp'
                });
            }else if(obj.event==='deleteAll'){
                var data = checkStatus.data;
                var ids = [];
                $(data).each(function() {
                    ids.push(this.id)
                });
                if(ids.length==0){
                    mylayer.errorMsg("选中不能为空");
                    return ;
                }
                layer.confirm('真的删除行么', function(index){
                    $.post(
                        '<%=request.getContextPath()%>/manager?method=deleteAll',
                        {'ids': ids},
                        function(result) {
                            if (result.code == 0) {
                                mylayer.okMsg(result.msg);
                                //删除之后重新刷新这个表格
                                table.reload('tableId');
                            }
                        },
                        'json'
                    );
                });
            }
        })
        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    deleteById(data.id);
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    title:'编辑',
                    type: 2,
                    area: ['550px', '500px'],
                    content: '<%=request.getContextPath()%>/manager?method=getManagerUpdatePage&id=' + data.id
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
</script>
</body>
</html>
