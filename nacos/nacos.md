# Nacos
本次项目的服务发现主要使用Nacos，使用Docker运行Nacos镜像

1. 拉取镜像
docker pull nacos/nacos-server

2. 启动容器
docker  run \
   --name nacos -d \
   -p 8848:8848 \
   --privileged=true \
   --restart=always \
   -e JVM_XMS=256m \
   -e JVM_XMX=256m \
   -e MODE=standalone \
   -e PREFER_HOST_MODE=hostname \
   -v /home/nacos/logs:/home/nacos/logs \
   -v /home/nacos/init.d/custom.properties:/home/nacos/init.d/custom.properties \
   nacos/nacos-server