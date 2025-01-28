# 使用支持 Java 19 的基础镜像
FROM eclipse-temurin:19-jdk-jammy

# 设置工作目录
WORKDIR /app

# 将 Spring Boot 可执行 JAR 文件复制到容器中
COPY target/fraud-detection*.jar app.jar

# 将 `application.yml` 文件复制到容器中
COPY src/main/resources/application.yml /app/config/application.yml

# 设置环境变量指向配置文件路径
ENV SPRING_CONFIG_LOCATION=/app/config/

# 将服务账户密钥文件复制到容器中
COPY ./spheric-engine-448906-m3-3aa7849c9788.json /app/credentials/service-account-key.json

# 设置 GOOGLE_APPLICATION_CREDENTIALS 环境变量
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/credentials/service-account-key.json

# 暴露应用运行的端口
EXPOSE 8080

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
