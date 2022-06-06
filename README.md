# 创新实践项目2022春

### 项目名称：基于Spring Boot开发的编程资源导航系统网站

------

### 项目开发环境：

- 开发工具：IntelliJ IDEA 
- 数据库：MySQL8
- jdk1.8

------

### 项目成果访问方式

- 前台：[https://pns.weiyuexin.top/](https://pns.weiyuexin.top/)
- 后台管理系统：[https://admin.pns.weiyuexin.top/](https://admin.pns.weiyuexin.top/)

------

### 项目所用技术

- 前端
  - layui、JQuery、css、html、UEditor、Font-Awesome、LAYUI MINI
- 后端：
  - SpringBoot
  - [ MyBatis-Plus](https://baomidou.com/)
  - Hutool
- 服务器：
  - 系统：CentOS 7
  - 反向代理：nginx

------

### 项目特色

- 所有图片及文件都存储在腾讯云对象存储，保证了静态资源的访问速度和安全性
- 使用IP工具，可以对用户登录、发布文章的资源时的IP地址的IP属地进行管理
- 实现了邮箱验证码功能，可以使用验证码注册、登录以及重置密码
- 界面设计美观，使用方便简洁
- 实现模糊搜索功能，前台可以搜索文章，后台管理系统可以对所有管理的内容进行搜索
- 对用户密码进行md5加密，保证了系统的安全性
- 后台管理系统可以查看用户的登录操作日志
