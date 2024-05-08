<p align="center">
    <img width="800" src="image/aifusion.png" alt="AIFUSION LOGO" />
    <b>Ai Fusion</b>，基于Java的可商业开源AI应用平台
</p>

------------------------

## 功能预览
![chat.png](image%2Fchat.png)

## 平台功能
* [x] 模型配置，当前已支持Gemini、ChatGPT模型
* [x] AI助手配置
* [x] 用户积分
* [x] 流式聊天
* [x] 对话记录

### 待完成
* [ ] 更多模型接入
* [ ] 更完善的前端界面
* [ ] 购买积分
* [ ] 前端界面开源


## 快速开始

```
git clone https://github.com/Stonewuu/ai-fusion.git
cd ai-fusion
mvn package
```

启动配置位于 ``ai-fusion-server`` 中的 ``src/main/resources/application-{env}.yml`` 中
首次启动只需要关注数据库和redis的连接配置项，以 ``${spring.datasource.dynamic.datasource.slave.name}`` 配置名为数据库名提前创建好数据库，启动时将会自动建表

```
server:
  port: 48080
--- #################### 数据库相关配置 ####################
spring:
  ...
  # 数据源配置项
  datasource:
    ...省略
    dynamic: # 多数据源配置
      ...省略
      primary: master
      datasource:
        master:
          name: ai-fusion
          url: jdbc:mysql://127.0.0.1:3306/${spring.datasource.dynamic.datasource.master.name}?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true # MySQL Connector/J 8.X 连接的示例
          username: root
          password: ai-fusion
        slave: # 模拟从库，可根据自己需要修改
          name: ai-fusion
          lazy: true # 开启懒加载，保证启动速度
          url: jdbc:mysql://127.0.0.1:3306/${spring.datasource.dynamic.datasource.slave.name}?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true # MySQL Connector/J 8.X 连接的示例
          username: root
          password: ai-fusion

  # Redis 配置。Redisson 默认的配置足够使用，一般不需要进行调优
  data:
    redis:
      host: 127.0.0.1 # 地址
      port: 6379 # 端口
      database: 0 # 数据库索引
#    password: dev # 密码，建议生产环境开启
```

## 感谢
[ruoyi-vue-pro](https://github.com/YunaiV/ruoyi-vue-pro) 