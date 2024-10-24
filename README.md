# 学生宿舍管理系统

#### 介绍
一个学生宿舍管理系统（jsp+servlet），功能齐全，jsp页面使用了layui，JQuery，layer，ECharts等，后端使用maven搭建，用tomcat7插件运行，使用了jstl，jackson，lombok，commons-fileupload等插件。简单模拟了权限管理（只有admin才有超级权限，能够管理其他系统用户），使用MD5进行密码加密。

![login.png](https://gitee.com/sun-yangzhen/student-dormitory-system/raw/master/images/login.png)

![pic1.png](https://gitee.com/sun-yangzhen/student-dormitory-system/raw/master/images/pic1.png)

![pic2.png](https://gitee.com/sun-yangzhen/student-dormitory-system/raw/master/images/pic2.png)

![pic3.png](https://gitee.com/sun-yangzhen/student-dormitory-system/raw/master/images/pic3.png)



#### 背景资料：

   1）每个学生有学号、姓名、年龄、专业、班级、手机号、性别等属性；

   2）每个宿舍楼有楼号、管理员、房间数、性别、信息等属性；

   3）每个宿舍有宿舍号、住宿人数、信息、所属宿舍楼等属性；

   4）每个管理员有姓名、性别、照片等属性；

#### 设计要求：

   1）人员信息管理，包括学生和管理员的增加、删除、修改和查询；

   2）校区住宿楼信息管理；

   3）宿舍信息管理；

   4）学生入住: 按宿舍情况、按照学生班级、专业信息分配学生宿舍，记录每个宿舍入住学生信息；

   5）转宿、退宿管理。

   6）实现灵活的查询方式：按学生专业、班级信息查询学生宿舍分布；按宿舍查学生信息。
