# 指定基础镜像，在其上进行定制
# 启动sentinel-dashboard需要依赖JDK环境（最好是JDK1.8版本或以上）
FROM java:8

# 维护者信息
MAINTAINER shanzhaozhen <204447118@qq.com>

# 复制上下文目录下的jar包到容器里  使用COPY命令亦可
ADD sentinel-dashboard-1.8.0.jar sentinel-dashboard.jar
ADD application.yml application.yml

# 声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 18080

# 指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
# JAVA_OPTS 可以在外部传入java启动参数 如 docker run xxx -e "JAVA_OPTS=xxx" -e "JAVA_ARGS=xxx"


# 可以使用的参数： -Xms64m -Xmx256m -Dsentinel.dashboard.auth.username=admin -Dsentinel.dashboard.auth.account=admin123 -Dserver.servlet.session.timeout=120
#ENTRYPOINT ["java", " ${JAVA_OPTS} -jar", "sentinel-dashboard.jar ${JAVA_ARGS}"]
ENTRYPOINT ["java", "-jar", "sentinel-dashboard.jar"]

# 在镜像运行为容器后执行的命令
