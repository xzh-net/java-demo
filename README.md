# java-demo

## 1. 工程构建与部署

> Servlet war 工程的本地调试、持续集成与制品仓库发布

- [Servlet 集成 Tomcat7 插件](servlet-idea) — IDEA 中使用 Maven Tomcat7 插件运行调试 Servlet 项目

- [Jenkins 持续集成 war 项目](servlet-jenkins) — Jenkins 流水线构建打包并部署 Servlet war 项目

- [Maven 项目部署到 Nexus3 仓库](servlet-nexus) — Maven 项目打包发布到私有 Nexus3 制品仓库

---

## 2. 源码分析与手写实现

> 剖析框架源码原理，并以手写或模拟方式复刻核心机制

- [Spring 解决循环依赖](spring) — Spring 容器三级缓存原理与循环依赖源码分析示例

- [MyBatis 配置与自定义插件](mybatis) — MyBatis 整合 Log4j2 输出 SQL 日志，并演示乐观锁、字段加解密脱敏、SQL 监控等自定义插件

- [手写 MyBatis-Spring 集成](spring-mybatis) — 手写模拟 MyBatis-Spring 集成原理：自定义 FactoryBean、Mapper 扫描注册与 JDK 动态代理增强

- [手写 RPC 框架（模拟 Dubbo）](dubbo) — 手写模拟 Dubbo 设计思想的简易 RPC 框架：动态代理 + HTTP/Netty 协议 + 本地/ZooKeeper 注册中心

---

## 3. Web 与网络通信

> Web 框架零 XML 配置演进与网络编程、即时通讯实战

- [SpringMVC 零 XML 配置](springmvc-embed) — 基于 WebApplicationInitializer 实现纯注解零 XML 配置的 SpringMVC

- [Netty 网络编程](netty) — Netty 核心组件与编解码器使用示例

- [XMPP Openfire Smack 开发](openfire) — 基于 XMPP 协议使用 Openfire 服务器和 Smack 客户端开发即时通讯

---

## 4. 大数据流处理

> Flume、Pulsar、Flink、Kafka 组成的日志采集与流式数据处理链路

- [Flume 日志发送至 Pulsar](flume-ng-pulsar-sink) — 自定义 Flume Sink 将日志数据发送至 Apache Pulsar

- [Flume 自定义组件](flume-ng-plug) — Flume 自定义组件示例：拦截器按事件内容添加标记头，另含自定义 Source 与 Sink 实现

- [Pulsar Function 与 Flink 调用](pulsar-flink) — Pulsar 自定义 Function 处理消息，通过 Flink Connector 实现与 Pulsar 的双向数据读写

- [Flink + Kafka 数据处理](flink-kafka) — 基于 Flink + Kafka 的通用流式数据处理流程示例
