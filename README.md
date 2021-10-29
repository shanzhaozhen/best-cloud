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

#### 1. 启动Nacos（官方推荐的源码构建方式）
1. 下载/更新源码或下载发行包
```shell
# 你可以通过源码和发行包两种方式来获取 Nacos。

# 1. 从 Github 上下载源码方式
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos '-Dmaven.test.skip=true' clean install -U  
ls -al distribution/target/

# 切换到编译路径
# change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin


# 2. 下载编译后压缩包方式
# 您可以从 最新稳定版本 下载 nacos-server-$version.zip 包。

unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
cd nacos/bin
```

2. 启动Nacos

Linux/Unix/Mac 启动命令(standalone代表着单机模式运行，非集群模式):
`sh startup.sh -m standalone`

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：
`bash startup.sh -m standalone`

Windows 启动命令(standalone代表着单机模式运行，非集群模式):
`startup.cmd -m standalone`
