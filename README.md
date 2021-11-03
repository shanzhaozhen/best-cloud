# best-admin

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

微服务组件的更新实在是太快了，短短半年时间Netflix对微服务的各种组件各种停更、定制维护，据说原因有团队的调整、微服务的服务方便有所改变。但是由于停更的原因我们也需要用其他组件时间在原来基础的功能甚至拓展这些功能。该项目的技术栈线主要使用Spring Cloud Alibaba

组件功能 | 组件
:---:|:---:
服务发现|Nacos
配置中心|Nacos
服务熔断|Sentinel
分布式事务|Seata
消息队列|RocketMQ

### 版本说明
[Spring Cloud Alibaba 版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

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

### 建表语句
基础服务建表语句:
在sql文件夹下

oauth2授权信息持久化建表：
https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql

生成密钥库jwt.jks
``` shell script
keytool -genkey -alias best-cloud -keyalg RSA -keypass 123456 -storepass 123456 -keystore jwt.jks


-genkey 生成密钥

-alias 别名

-keyalg 密钥算法

-keypass 密钥口令

-keystore 生成密钥库的存储路径和名称

-storepass 密钥库口令
```

### 环境要求



### 执行指南

#### 1. 启动 Nacos ([详细文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md))

1. 下载/更新源码或下载发行包，[详细点击查看](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)
   1. 直接下载：[Nacos Server 下载页](https://github.com/alibaba/nacos/releases)
   2. 源码构建：进入 Nacos [Github 项目页面](https://github.com/alibaba/nacos) ，将代码 git clone 到本地自行编译打包，[参考此文档](https://nacos.io/zh-cn/docs/quick-start.html) 。

2. 启动Nacos

Linux/Unix/Mac 启动命令(standalone代表着单机模式运行，非集群模式):
`sh startup.sh -m standalone`

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：
`bash startup.sh -m standalone`

Windows 启动命令(standalone代表着单机模式运行，非集群模式):
`startup.cmd -m standalone`

* Docker 启动

```shell
mkdir -p /home/nacos/logs/                      #新建logs目录
mkdir -p /home/nacos/init.d/
vim /home/nacos/init.d/custom.properties        #修改配置文件

docker run \
  --name nacos-standalone \
  -e MODE=standalone -p 8848:8848 \
  -d nacos/nacos-server:latest
```

#### 2. 启动 Sentinel ([详细文档](http://edas-public.oss-cn-hangzhou.aliyuncs.com/install_package/demo/sentinel-dashboard.jar))

1. 首先需要获取 Sentinel 控制台，支持直接下载和源码构建两种方式。

   1. 直接下载：[下载 Sentinel 控制台](http://edas-public.oss-cn-hangzhou.aliyuncs.com/install_package/demo/sentinel-dashboard.jar)
   2. 源码构建：进入 Sentinel [Github 项目页面](https://github.com/alibaba/Sentinel)，将代码 git clone 到本地自行编译打包，[参考此文档](https://github.com/alibaba/Sentinel/tree/master/sentinel-dashboard)。

2. 启动控制台，执行 Java 命令 `java -jar sentinel-dashboard.jar`完成 Sentinel 控制台的启动。
   控制台默认的监听端口为 8080。Sentinel 控制台使用 Spring Boot 编程模型开发，如果需要指定其他端口，请使用 Spring Boot 容器配置的标准方式，详情请参考 [Spring Boot 文档](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-customizing-embedded-containers)。

* Docker 启动

  Sentinel 没有官方的镜像，所以使用Dockerfile构建镜像`(请看路径/middleware/sentinel)`

    ```shell
    docker run \
      --name sentinel \
      -p 18080:18080 \
      -d sentinel-dashboard:1.8.0
    ```