
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
    <title>宿舍列表</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<style>
    .left-container::-webkit-scrollbar {
        display: none;
    }
</style>
<div class="left-container" style="float:left;width: 8%;height: 100%;display: flex;border-radius: 0px;
overflow-x: scroll;
 overflow-y: scroll;">
    <div class="layui-panel" style="flex-shrink: 0;width: 100%">
        <ul class="layui-menu" id="leftMenu">
            <li lay-options="{buildingTag:''}" style="text-align:center">
                全部
            </li>
        </ul>
    </div>
</div>
<div class="right-container" style="float: right;width: 92%">
    <div class="demoTable" style="margin-top: 7px">
        宿舍号：
        <div class="layui-inline">
            <input class="layui-input" name="tag" id="tagId" autocomplete="off">
        </div>
        <button class="layui-btn" data-type="reload">搜索</button>
    </div>
    <table class="layui-hide" id="test" lay-filter="test"></table>
</div>
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
    {{#     if(d.buildingGender=="男") {            }}
    <span class="layui-badge layui-bg-blue" style="position: relative; ">男生宿舍楼</span>
    {{#     } else if(d.buildingGender=="女"){     }}
    <span class="layui-badge layui-bg-orange" style="position: relative; ">女生宿舍楼</span>
    {{#     }                          }}

</script>


<script>
    var $=layui.jquery;
    var layer=layui.layer;
    var table;
    $.post(
        '<%=request.getContextPath()%>/building?method=selectAll',
        function (result) {
            //console.log(result);
            $(result).each(function () {
                $('#leftMenu').append('<li style="text-align:center" lay-options="{buildingTag:\''+ this.tag+'\'}">'+this.tag+'['+this.gender+']'+'</li>');
            });
        },
        'json'
    );

    function deleteById(id) {
        <%--location.href = '${pageContext.request.contextPath}/user?method=deleteById&id=' + id;--%>

        $.post(
            '<%=request.getContextPath()%>/dormitory?method=deleteById',
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
         table = layui.table;

        table.render({
            elem: '#test'
            ,skin:'row'
            ,url:'<%=request.getContextPath()%>/dormitory?method=selectByPage'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', ]
            ,title: '宿舍数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID', width:50, fixed: 'left',sort: true}
                ,{field:'tag', title:'宿舍号', width:100 }
                ,{field:'contain', title:'容纳人数', width:110, sort: true}
                ,{field:'note', title:'信息' }
                ,{field:'buildingTag', title:'所属公寓', width:100 }
                ,{field:'buildingNote', title:'公寓信息' }
                ,{field:'buildingGender', title:'公寓类别', width:120,templet:'#genderTemplet'}
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
                    content: '<%=request.getContextPath()%>/Dormitory/dormitory_add.jsp'
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
                        '<%=request.getContextPath()%>/dormitory?method=deleteAll',
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
                layer.confirm('注意！删除宿舍后，其入住的学生将自动退宿', function(index){
                    deleteById(data.id);
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    title:'编辑',
                    type: 2,
                    area: ['550px', '500px'],
                    content: '<%=request.getContextPath()%>/dormitory?method=getDormitoryUpdatePage&id=' + data.id
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
                        tag: $('#tagId').val()
                    }
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    layui.use('dropdown', function(){
        var dropdown = layui.dropdown;
        dropdown.on('click(leftMenu)', function(options){
            var buildingTag=options.buildingTag;
            table.reload('tableId', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    tag: $('#tagId').val(),
                    buildingTag: buildingTag
                }
            });
        });
    });
    $('body').on("mouseenter",".layui-table-body tr",function () {
        $(this).siblings().find("div").css("color","#666");
        $(this).find("div").css("color","#4DA1FF");
    })
</script>
</body>
</html>
