
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
    <title>公寓列表</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<div class="demoTable layui-form layui-row" style="margin-top: 7px">
    楼号：
    <div class="layui-inline">
        <input class="layui-input" name="tag" id="tagId" autocomplete="off">
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
    <div class="layui-badge layui-bg-blue" style="position: absolute;margin: auto;left:0px;
               right: 0px;height: 18px;width: 65%;
               top:0px;
               bottom: 0px;">男生公寓</div>
    {{#     } else{     }}
    <span class="layui-badge layui-bg-orange" style="position: absolute;margin:auto; left:0px;
               right: 0px;height: 18px;width: 65%;
               top:0px;
               bottom: 0px; ">女生公寓</span>
    {{#     }                          }}
</script>

<script type="text/html" id="managerTemplet">
    {{#     if(d.managerGender=="男") {            }}
    <span class="layui-badge layui-bg-blue" style="position: relative; ">{{d.managerName+'['+d.managerGender+']'}}</span>
    {{#     } else if(d.managerGender=="女"){     }}
    <span class="layui-badge layui-bg-orange" style="position: relative; ">{{d.managerName+'['+d.managerGender+']'}}</span>
    {{#     }else{                                }}
             <a>无</a>
    {{#     }                               }}

    <div class="layui-btn-group">
        {{#   if(d.managerName==null){                            }}
        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="addManager">
            <i class="layui-icon">&#xe654;</i>
        </button>
        {{#   }else{             }}
        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="changeManager">
            <i class="layui-icon">&#xe642;</i>
        </button>
        <button type="button" class="layui-btn layui-btn-primary layui-btn-xs" lay-event="delManager">
            <i class="layui-icon">&#xe67e;</i>
        </button>
        {{#   }                    }}
    </div>
</script>

<script>
    var $=layui.jquery;
    var layer=layui.layer;
    function deleteById(id) {
        $.post(
            '<%=request.getContextPath()%>/building?method=deleteById',
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
            ,url:'<%=request.getContextPath()%>/building?method=selectByPage'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', ]
            ,title: '宿舍楼数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID', width:80, fixed: 'left',sort: true}
                ,{field:'tag', title:'楼号', width:150 ,sort: true}
                ,{field:'roomsMax', title:'最大房间数', width:150, sort: true}
                ,{field:'note', title:'信息', width:200 }
                ,{field:'gender', title:'类别', width:100,templet:'#genderTemplet'}
                ,{field:'managerNameView', title:'宿管', templet:'#managerTemplet'}
                ,{field:'managerName', title:'宿管名字',hide:true}
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
                    content: '<%=request.getContextPath()%>/Building/building_add.jsp'
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
                        '<%=request.getContextPath()%>/building?method=deleteAll',
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
                layer.confirm('注意！删除公寓后，与之关联的宿舍和宿管将需要重新设置所属公寓楼', function(index){
                    deleteById(data.id);
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    title:'编辑',
                    type: 2,
                    area: ['550px', '500px'],
                    content: '<%=request.getContextPath()%>/building?method=getBuildingUpdatePage&id=' + data.id
                });
            }else if(obj.event==='addManager'){
                var data = obj.data;
                //console.log(data);
                layer.open({
                    title:'添加宿管',
                    type: 2,
                    area: ['550px', '400px'],
                    success: function (layero, index) {    //成功获得加载changefile.html时，预先加载，将值从父窗口传到 子窗口
                        let body = layer.getChildFrame('body', index);
                        body.find("#buildingId").val(data.id);
                        body.find("#buildingTagId").val(data.tag);
                        layui.form.render();
                    },
                    content: '<%=request.getContextPath()%>/Building/addManager.jsp'
                });

            }else if(obj.event==='delManager'){
                var data = obj.data;

                layer.confirm('确定取消此管理员对此公寓楼的管理？', function(index){
                    $.post(
                        '<%=request.getContextPath()%>/buildingManager?method=delete',
                        {'buildingId': data.id},
                        function(result) {
                            if (result.code == 0) {
                                mylayer.okMsg(result.msg);
                                //删除之后重新刷新这个表格
                                table.reload('tableId');
                            }else{
                                mylayer.errorMsg(result.msg);
                            }
                        },
                        'json'
                    );
                });
            }else if(obj.event==='changeManager'){
                var data = obj.data;
                layer.open({
                    title:'更换宿管',
                    type: 2,
                    area: ['550px', '400px'],
                    success: function (layero, index) {    //成功获得加载changefile.html时，预先加载，将值从父窗口传到 子窗口
                        let body = layer.getChildFrame('body', index);
                        body.find("#buildingId").val(data.id);
                        body.find("#buildingTagId").val(data.tag);
                        body.find("#managerNameId").val(data.managerName);
                        layui.form.render();
                    },
                    content: '<%=request.getContextPath()%>/Building/changeManager.jsp'
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
                        tag: $('#tagId').val(),
                        gender: $('#genderId').val()
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
