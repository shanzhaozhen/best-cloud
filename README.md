# best-admin

### 介绍
该项目主要是一个用于开发的基础框架，以供给自己快速开发项目，也准备制作一般以Spring Cloud技术栈实现的

* [后台项目地址](https://github.com/shanzhaozhen/best-server)
* [Spring Cloud实现项目地址](https://github.com/shanzhaozhen/best-cloud)
* [前端项目地址](https://github.com/shanzhaozhen/best-client)
* [Demo站点](http://best.loogoos.tk)
    > 仅支持ipv6用户访问，因为demo站点是使用我私有的小服务器搭建的的ipv6的方式映射的，[详见：我另外一个仓库（暂时没完善）](https://github.com/shanzhaozhen/MyNAS)
    > 默认账号：123456
    > 默认密码：123456

### 功能实现
实现功能 | 是否已实现
:---:|:---:
JWT验证登陆 | √
RABC动态权限管理 | √
动态分配菜单 | √
Swagger2-api文档查看、导出 | √
动态配置定时任务 | √
动态定时任务增加第三方http接口的调用 | -
加入 Oauth2 实现第三方登陆 | -
多种方式授权 | -
加入Spring Cloud 微服务化 | -
假如Dockerfile直接打包部署到服务器|√ 

### 相关技术
该项目使用的相关技术/工具主要有:
* Spring Boot
* Spring Security
* JWT
* Druid
* Vue
* Redis
* Swagger2
* Docker