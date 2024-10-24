<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2023/8/11
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body::-webkit-scrollbar {
        display: none;
    }
</style>
<head>
    <title>数据分析</title>
    <script src="<%=request.getContextPath()%>/static/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/static/echarts.min.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<style>
    #bg-container{ width: 99%;height: auto;position: absolute}
    .inner-container{background-color: rgba(169,169,169,0.1);width: 80%;height: 550px;margin: auto;margin-top: 100px;
                position: relative;border-radius: 20px;
    }
    .echarts-container{ margin:auto;height: 100%;}
    .echarts-one{width: 90%;}
    .echarts-left{width: 45%;position: absolute;left:5%}
    .echarts-right{width: 45%;position: absolute;right: 5%}
</style>


<div id="bg-container">
    <div class="inner-container">
        <div class="echarts-container echarts-one"  id="building-maxdor"></div>
    </div>
    <div class="inner-container">
        <div class="echarts-container echarts-one" id="building-student">
        </div>
    </div>
    <div class="inner-container">
        <div class="echarts-container echarts-left" id="student-gender"></div>
        <div class="echarts-container echarts-right" id="manager-gender"></div>
    </div>
</div>

<script type="text/javascript">
    var studentGender = document.getElementById('student-gender');
    var managerGender = document.getElementById('manager-gender');
    var buildingMaxdor = document.getElementById('building-maxdor');
    var buildingStudent = document.getElementById('building-student');
    var studentGenderE = echarts.init(studentGender);
    var managerGenderE = echarts.init(managerGender);
    var buildingMaxdorE = echarts.init(buildingMaxdor);
    var buildingStudentE = echarts.init(buildingStudent);
    var genderEData;
    var buildingEData;
    var buildingStudentEData;

    genderEData = {
        title: {
            text: '性别',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: '性别',
                type: 'pie',
                radius: '50%',
                data: [
                    { value: 0, name: '男' },
                    { value: 0, name: '女' }
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    $.post(
        '<%=request.getContextPath()%>/student?method=queryGender',
        function(jsonObj) {
            genderEData.series[0].data[0].value=jsonObj[0];
            genderEData.series[0].data[1].value=jsonObj[1];
            genderEData.title.text='学生'
            studentGenderE.setOption(genderEData);
        },
        'json'
    );
    $.post(
        '<%=request.getContextPath()%>/manager?method=queryGender',
        function(jsonObj) {
            genderEData.series[0].data[0].value=jsonObj[0];
            genderEData.series[0].data[1].value=jsonObj[1];
            genderEData.title.text='宿舍管理员'
            managerGenderE.setOption(genderEData);
        },
        'json'
    );

    buildingEData = {
        title: {
            text: '宿舍楼房间数据'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                // Use axis to trigger tooltip
                type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
            }
        },
        legend: {},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: ['A1', 'A2','B1','B2','C1','C2']
        },
        series: [
            {
                name: '已分配',
                type: 'bar',
                stack: 'total',
                label: {
                    show: true
                },
                emphasis: {
                    focus: 'series'
                },
                data: [0,0,0,0,0,0]
            },
            {
                name: '最大房间数',
                type: 'bar',
                stack: 'total',
                label: {
                    show: true
                },
                emphasis: {
                    focus: 'series'
                },
                data: [0,0,0,0,0,0]
            }
        ]
    };
    $.post(
        '<%=request.getContextPath()%>/building?method=queryAllocation',
        function(jsonObj) {
            var tags=[];
            var hasAllocation=[];
            var roomsMax=[];
            for(var index in jsonObj){
                var v=jsonObj[index];
                tags.push(v.tag);
                hasAllocation.push(v.hasAllocation);
                roomsMax.push(v.roomsMax);
            }
            buildingEData.yAxis.data=tags;
            buildingEData.series[0].data=hasAllocation;
            buildingEData.series[1].data=roomsMax;
            buildingMaxdorE.setOption(buildingEData);
        },
        'json'
    );
    buildingStudentEData = {
        title: {
            text: '宿舍楼学生数据'
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: [120, 200, 150, 80, 70, 110, 130],
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                }
            }
        ]
    };
    $.post(
        '<%=request.getContextPath()%>/building?method=queryStudentNum',
        function(jsonObj) {
            var tags=[];
            var studentNum=[];
            for(var index in jsonObj){
                var v=jsonObj[index];
                tags.push(v.tag);
                studentNum.push(v.studentNum);

            }

            buildingStudentEData.xAxis.data=tags;
            buildingStudentEData.series[0].data=studentNum;
            buildingStudentE.setOption(buildingStudentEData);
        },
        'json'
    );

</script>
</body>
</html>
