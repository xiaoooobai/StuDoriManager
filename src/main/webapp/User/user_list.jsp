<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body::-webkit-scrollbar {
        display: none;
    }
</style>
<head>
    <title>用户列表</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
    <div class="demoTable layui-form layui-row" style="margin-top: 7px">
        用户名：
        <div class="layui-inline">
            <input class="layui-input" name="name" id="nameId" autocomplete="off">
        </div>
        <button class="layui-btn" data-type="reload">搜索</button>
    </div>

    <table class="layui-hide" id="test" lay-filter="test"></table>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="deleteAll">批量删除</button>
        </div>
    </script>

    <script type="text/html" id="barDemo">

        {{#     if(d.username=="admin") {            }}
        <a class="layui-btn  layui-btn-xs" >无法删除</a>
        {{#     } else{     }}
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        {{#     }                          }}
    </script>

    <script type="text/html" id="statusTemplet">
        <input type="checkbox" name="{{d.username}}" value="{{d.id}}"  lay-skin="switch" lay-text="激活|禁用" lay-filter="statusLayFilter" {{ d.status == 0 ? 'checked' : '' }}>
    </script>


    <script>
        layui.use('table', function(){
            var table = layui.table;
            var form = layui.form;

            table.render({
                elem: '#test'
                ,url:'<%=request.getContextPath()%>/user?method=selectByPage'
                ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
                ,defaultToolbar: ['filter', 'exports', 'print']
                ,title: '用户数据表'
                ,cols: [[
                    {type: 'checkbox', fixed: 'left'}
                    ,{field:'id', title:'ID', fixed: 'left'}
                    ,{field:'username', title:'用户名'}
                    ,{field:'password', title:'密码'}
                    ,{field:'nickname', title:'昵称'}
                    ,{field:'status', title:'状态',templet: '#statusTemplet'}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
                ]]
                ,page: true
                ,id: 'tableId'
            });

            //监听状态切换操作
            form.on('switch(statusLayFilter)', function(obj){

                var status = obj.elem.checked == true ? 0 : 1;
                if(this.name=='admin'){
                    obj.elem.checked=true;
                    mylayer.errorMsg("无权限更改超级管理员的状态");
                    layui.form.render('checkbox');
                    return;
                }
                $.post(
                    '<%=request.getContextPath()%>/user?method=updateStatus',
                    {'id':this.value, 'status': status},
                    function(result) {
                        if (result.code == 0) {
                            mylayer.okMsg(result.msg);
                        }else {
                            mylayer.errorMsg(result.msg);
                        }
                    },
                    'json'
                );
            });

            //头工具栏事件
            table.on('toolbar(test)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id);
                switch(obj.event){
                    case 'deleteAll':
                        var data = checkStatus.data;
                        var ids = [];
                        $(data).each(function() {
                            ids.push(this.id)
                        });
                        //[2,4]
                        layer.confirm('真的删除行么', function(index){
                            $.post(
                                '<%=request.getContextPath()%>/user?method=deleteAll',
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
                        break;
                }
            });

            //监听行工具事件
            table.on('tool(test)', function(obj){
                //{id: 2, name: 'zhansgan', password: '23', age: 0, gender: null}
                var data = obj.data;
                //console.log(obj)
                if(obj.event === 'del'){
                    layer.confirm('真的删除行么', function(index){
                       $.post(
                           '<%=request.getContextPath()%>/user?method=deleteById',
                           {'id': data.id},
                           function(result) {
                               if (result.code == 0) {
                                   mylayer.okMsg(result.msg);
                                   //删除之后重新刷新这个表格
                                   table.reload('tableId');
                               }else{
                                   mylayer.okMsg(result.msg);
                               }
                           },
                           'json'
                       );
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
                            // /user?method=selectByPage&page=1&limit=10&name=zhasang&gender=男
                            username: $('#nameId').val(),
                        }
                    });
                }
            };

            $('.demoTable .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

        });
    </script>
</body>
</html>
