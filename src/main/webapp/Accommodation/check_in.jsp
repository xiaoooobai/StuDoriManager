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
  <title>已入住</title>
  <%@ include file="../header.jsp"%>
</head>
<body>
<style>
  .left-container::-webkit-scrollbar {
    display: none;
  }
</style>
<ul class="layui-nav" lay-filter="" style="border-radius: 0px;position: relative;top:-9px">
  <li class="layui-nav-item layui-this"><a href="<%=request.getContextPath()%>/Accommodation/check_in.jsp">已入住</a></li>
  <li class="layui-nav-item "><a href="<%=request.getContextPath()%>/Accommodation/not_check_in.jsp">未入住</a></li>
</ul>

<div class="left-container" style="float:left;width: 8%;height: 90%;display: flex;border-radius: 0px;
overflow-x: scroll;
 overflow-y: scroll;">
  <div class="layui-panel" style="flex-shrink: 0;width: 100%">
    <ul class="layui-menu" id="buildingMenu">

    </ul>
  </div>
</div>
<div class="left-container" style="float:left;left: 8%; width: 8%;height: 90%;display: flex;border-radius: 0px;
overflow-x: scroll;
 overflow-y: scroll;">
  <div class="layui-panel" style="flex-shrink: 0;width: 100%">
    <ul class="layui-menu" id="dormitoryMenu">
      <li lay-options="{dormitoryTag:'',dormitoryId:''}" style="text-align:center;font-size: small">
        全部宿舍
      </li>
    </ul>
  </div>
</div>
<div class="right-container" style="float: right;width: 84%">
  <div class="demoTable layui-form layui-row" style="margin-top: 7px;">
    名字：
    <div class="layui-inline" style="width: 15%;">
      <input class="layui-input" name="name" id="nameId" autocomplete="off">
    </div>
    性别：
    <div class="layui-inline" style="width: 15%;">
      <select name="gender" id="genderId">
        <option value="">全部性别</option>
        <option value="男">男</option>
        <option value="女">女</option>
      </select>
    </div>
    专业：
    <div class="layui-inline" style="width: 15%;">
      <input class="layui-input" name="major" id="majorId" autocomplete="off">
    </div>
    班级：
    <div class="layui-inline" style="width: 15%;">
      <input class="layui-input" name="stuclass" id="stuclassId" autocomplete="off">
    </div>
    <button class="layui-btn" data-type="reload">搜索</button>
  </div>
  <table class="layui-hide" id="test" lay-filter="test"></table>
</div>
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="changeDor">转宿</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="checkOut">退宿</a>
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
  var table;
  $.post(
          '<%=request.getContextPath()%>/building?method=selectAll',
          function (result) {
            //console.log(result);
            $(result).each(function () {
              $('#buildingMenu').append('<li style="text-align:center" lay-options="{buildingId:\''+ this.id+'\'}">'+this.tag+'['+this.gender+']'+'</li>');
            });
          },
          'json'
  );
  layui.use('table', function(){
    table = layui.table;

    table.render({
      elem: '#test'
      ,skin:'row'
      ,url:'<%=request.getContextPath()%>/student?method=selectHasCheckIn'
      ,title: '已入住学生数据表'
      ,cols: [[
        {field:'id', title:'ID', width:80, fixed: 'left',sort: true}
        ,{field:'name', title:'姓名', width:110 }
        ,{field:'age', title:'年龄', width:100, sort: true}
        ,{field:'major', title:'专业', width:110 }
        ,{field:'class_', title:'班级', width:120 }
        ,{field: 'buildingTag', title:'公寓'}
        ,{field: 'dorTag', title:'宿舍'}
        ,{field:'gender', title:'性别', width:70,templet:'#genderTemplet'}
        ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ,{fixed: 'dorId', title:'dorId', hide:true}
        ,{fixed: 'buildingId', title:'buildingId', hide:true}

      ]]
      ,page: true
      ,id: 'tableId'
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){
      var data = obj.data;
      if(obj.event === 'changeDor'){
        layer.open({
          title:'转宿',
          type: 2,
          area: ['550px', '500px'],
          success: function (layero, index) {    //成功获得加载changefile.html时，预先加载，将值从父窗口传到 子窗口
            let body = layer.getChildFrame('body', index);
            body.find("#studentId").val(data.id);
            body.find("#nameId").val(data.name);
            body.find("#genderId").val(data.gender);
            body.find("#majorId").val(data.major);
            body.find("#classId").val(data.class_);
            body.find("#nowBuildingId").val('[现在]'+data.buildingTag);
            body.find("#nowDorId").val('[现在]'+data.dorTag);
            layui.form.render();
          },
          content: '<%=request.getContextPath()%>/Accommodation/to_change_acc.jsp'
        });
      } else if(obj.event === 'checkOut'){
        //console.log(data);
        layer.confirm('退宿确认', function(index){
          $.post(
                  "<%=request.getContextPath()%>/stuDor?method=delete",
                  {'stuId':data.id,'dorId':data.dorId},
                  function (result){
                    if (result.code == 0) {
                      mylayer.okMsg(result.msg);
                      setInterval(function () {
                       location.reload();
                      }, 1500)
                    } else {
                      mylayer.errorMsg(result.msg);
                    }
                  },
                  'json'
          )
          layer.close(index);
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


  layui.use('dropdown', function(){
    var dropdown1 = layui.dropdown;
    var dropdown2 = layui.dropdown;
    dropdown1.on('click(buildingMenu)', function(options){
      var buildingId=options.buildingId;
      $('#dormitoryMenu').empty();
      $('#dormitoryMenu').append('<li style="text-align:center;font-size: small" lay-options="{dormitoryId:\'\'}">全部宿舍</li>');
      if(buildingId==null||buildingId==''){
        return;
      }
      $.post(
          '<%=request.getContextPath()%>/dormitory?method=selectByBuilding',
          {'buildingId': buildingId},
          function (jsonObj) {
            $(jsonObj).each(function () {
              $('#dormitoryMenu').append('<li style="text-align:center" lay-options="{dormitoryId:\''+ this.id+'\'}">'+this.tag+'</li>');
            });
          },
          'json'
      );
    });
    dropdown2.on('click(dormitoryMenu)', function(options){
      var dormitoryId=options.dormitoryId;
      table.reload('tableId', {
        page: {
          curr: 1 //重新从第 1 页开始
        }
        ,where: {
          tag: $('#tagId').val(),
          dormitoryId: dormitoryId
        }
      });

    });


  });



  $('body').on("mouseenter",".layui-table-body tr",function () {
    $(this).siblings().find("div").css("color","#666");
    $(this).find("div").css("color","#4DA1FF");
  })

  //注意：导航 依赖 element 模块，否则无法进行功能性操作
  // layui.use('element', function(){
  //   var element = layui.element;
  //
  //   //…
  // });
</script>
</body>
</html>
