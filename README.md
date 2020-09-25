# best-cloud

### 介绍
该项目主要是一个用于开发的基础框架，以供给自己快速开发项目，也准备制作一般以Spring Cloud技术栈实现的

* [后台项目地址](https://github.com/shanzhaozhen/best-server)
* [Spring Cloud实现项目地址](https://github.com/shanzhaozhen/best-cloud)
* [前端项目地址](https://github.com/shanzhaozhen/best-client)
* [Demo站点](http://best.loogoos.tk)
    > 仅支持ipv6用户访问，因为demo站点是使用我私有的小服务器搭建的的ipv6的方式映射的，[详见：我另外一个仓库（暂时没完善）](https://github.com/shanzhaozhen/MyNAS)
    > 默认账号：admin
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

### 微服务组件选型

微服务组件的更新实在是太快了，短短半年时间Netflix对微服务的各种组件各种停更、定制维护，据说原因有团队的调整、微服务的服务方便有所改变。但是由于停更的原因我们也需要用其他组件时间在原来基础的功能甚至拓展这些功能。

组件功能 | 组件
:---:|:---:
服务发现|Nacos 
配置中心|Nacos
服务熔断|Sentinel
分布式事务|Seata
消息队列|RocketMQ

### 相关技术
该项目使用的相关技术/工具主要有:
* Spring Cloud
* Spring Security
* JWT
* Druid
* Vue
* Redis
* Swagger2
* Docker

### 环境搭建
1. Nacos

这里的使用方式是Nacos Docker进行快速启动，[具体启动方式请看文档](https://nacos.io/zh-cn/docs/quick-start-docker.html)

```shell script
docker run --name nacos-standalone -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:latest
# 默认用户名/密码为：nacos/nacos
# 运行成功后打开http://(docker主机ip:端口号)/nacos
```

2. Sentinel
```shell script
docker run --name sentinel -p 8858:8858 -d bladex/sentinel-dashboard
```
