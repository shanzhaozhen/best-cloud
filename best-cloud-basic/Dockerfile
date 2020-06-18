##使用jdk8作为基础镜像
#FROM java:8
##指定作者
#MAINTAINER shanzhaozhen
##暴露容器的8088端口
#EXPOSE 8081
##将复制指定的best.jar为容器中的app.jar，相当于拷贝到容器中取了个别名
#ADD target/best.jar /app.jar
##创建一个新的容器并在新的容器中运行命令
#RUN bash -c 'touch /app.jar'
##设置时区
#ENV TZ=PRC
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
##相当于在容器中用cmd命令执行jar包  指定外部配置文件
#ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=/usr/local/project/docker/xxl-job/config/application.yml"]

# 使用java环境
FROM arm64v8/openjdk:8u212-jre
# 缓存目录
VOLUME /tmp
# 将当前项目的jar包添加到容器中
ADD "best-api/target/best.jar" "app.jar"
# 当容器启动时 执行启动命令
ENTRYPOINT ["java", "-jar", "app.jar", "&"]
