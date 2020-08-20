## 스프링부트 JPA, MySQL, Security 인스타그램

## 의존성

![image](https://user-images.githubusercontent.com/62128942/90703499-e9149c00-e2c8-11ea-9d54-26b5014264ca.png)

## DB 세팅

1. MySQL 한글 설정 (my.ini)

```
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
init_connect='SET collation_connection = utf8_general_ci'
character-set-server=utf8

```

2. 사용자 생성 및 권한 주기 및 DB 생성
   create user 'insta'@'%' identified by 'bitc5600';
   GRANT ALL PRIVILEGES ON _._ TO 'insta'@'%';
   create database insta;
   use insta;

## application.yml 설정

```
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/insta?serverTimezone=Asia/Seoul&useSSL=false&allowPublicKeyRetrieval=true
    username: insta
    password: bitc5600

  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: create
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      use-new-id-generator-mappings: false
    show-sql: true

  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: cos
      password: 1234

cos: #변수임
  secret: 겟인데어 #자바에서 가져다 쓸 수 있음

file:
  path: C:/src/springwork/instargram/src/main/resources/upload/

```
