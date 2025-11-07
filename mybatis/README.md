## MyBatis + Log4j2配置

## 查询数据的三种方式

1. namespace调用
2. Mapper调用
3. Annotation注解调用

## 插件

1. SQL执行统计
2. 乐观锁
3. 字段加解、脱敏

## SQL解析工具

```java
public class GenericTokenParserTest {
	public static void main(String[] args) {
		Map<String, Object> mapper = new HashMap<String, Object>();
		mapper.put("name", "张三");
		mapper.put("pwd", "123456");
		// 先初始化一个handler
		GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", new TokenHandler() {
			@Override
			public String handleToken(String content) {
				return (String) mapper.get(content);
			}
		});
		System.out.println("****" + genericTokenParser.parse("用户：${name}，你的密码是:${pwd}"));
	}
}
```

## 创建表结构

```sql
CREATE TABLE `ums_demo`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `version` int(255) NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
)

CREATE TABLE `ums_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) NULL DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户表' ROW_FORMAT = DYNAMIC;

```

## Log4j2配置

规则：按天滚动，每个10MB，每天最多30个，最少保存180天。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <!-- 控制台输出 -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        
        <!-- 按日期滚动的文件输出 -->
        <RollingFile name="RollingFileAppender"
                     fileName="logs/app.log"
                     filePattern="logs/app-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
            <Policies>
                <!-- 基于时间的触发策略：每天滚动 -->
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <!-- 基于大小的触发策略：单个文件达到10MB时滚动 -->
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <!-- 滚动策略配置 -->
            <DefaultRolloverStrategy>
                <!-- 每天最多30个文件 -->
                <Delete basePath="logs" maxDepth="1">
                    <!-- 删除180天前的文件 -->
                    <IfLastModified age="180d"/>
                    <!-- 按文件名模式匹配 -->
                    <IfFileName glob="app-*.log.gz"/>
                </Delete>
                <!-- 同一天内最多30个文件 -->
            </DefaultRolloverStrategy>
        </RollingFile>
    </Appenders>
    
    <Loggers>
        <!-- 根日志记录器，所有未被特定包覆盖到的info级别日志只输出到控制台 -->
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
        
        <!-- 特定包的日志级别 -->
        <Logger name="net.xzh" level="debug" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="RollingFileAppender"/>
        </Logger>
    </Loggers>
</Configuration>
```

