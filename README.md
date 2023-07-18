# spring-security-jwt-demo
spring-security兼容cas和jwt
# Spring-cas-jwt
本项目因为需求需要兼容spring-security和cas，还有jwt。
## 要求
1. spring-security和cas整合的url如果不通过跳转到cas-server的登录地址,cas登录成功后需要回来进行认证，cas还需要进行成功登录后进行获取当前的用户信息
2. spring-security和账号密码登录进行认证后进行置换jwt，以便于后续认证
## 说明
1. /login为账号密码登录的入口
2. /login/cas为cas登陆的入口

## 补充说明
集成官方的security-cas
