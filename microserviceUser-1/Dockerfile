FROM openjdk:8-jdk-slim
COPY "./microserviceUser-1-1.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar","ldap:// 50.17.250.110"]


