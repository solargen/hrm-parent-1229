# 一、配置中心服务端-集群版
## 1、注册到Eureka中
pom.xml
```xml
<!--eureka的客户端-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

```yaml
spring:
  application:
      name: autoconfig
  cloud:
    config:
      server:
        git:
          uri: autoconfig #仓库的地址
          searchPaths: src/main/resources #仓库的子目录中寻找配置文件

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/ #服务注册的地址
  instance:
    instance-id: autoconfig
    prefer-ip-address: true
```

## 2、配置中心客户端
启动服务发现，而不是使用具体的uri连接具体的配置中心服务端
pom.xml
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
```yaml
spring:
  cloud:
    config:
      profile: dev #环境
      name: application-zuul #名称
      #集群版配置中心
      discovery:
        service-id: config-server
        enabled: true
#      uri: http://localhost:6969 #单机版配置中心
```