## Maven项目部署到Nexus3仓库

## 配置Maven的settings.xml文件

首先需要在Maven的settings.xml文件中配置仓库的认证信息：

```xml
<servers>  
    <server>  
        <id>xzh-group</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>
    <server>  
        <id>xzh-hosted</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>
    <server>  
        <id>xzh-snapshots</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>
    <server>  
        <id>xzh-releases</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>   
</servers> 
<mirrors>
	<mirror>  
        <id>xzh-group</id>  
        <name>xzh-group</name>  
        <url>http://172.17.17.161:8081/repository/xzh-group/</url>  
        <mirrorOf>central</mirrorOf>  
    </mirror> 
</mirrors>
```

## 配置项目推送仓库地址

```xml
<distributionManagement>
    <repository>
        <id>xzh-releases</id>
        <url>http://172.17.17.161:8081/repository/xzh-releases/</url>
    </repository>
    <snapshotRepository>
        <id>xzh-snapshots</id>
        <url>http://172.17.17.161:8081/repository/xzh-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

## 执行Maven部署命令

在项目根目录下执行以下命令：

```bash
# 部署到仓库（会根据版本自动选择release或snapshot仓库）
mvn clean deploy

# 跳过测试
mvn clean deploy -DskipTests
```

## 验证部署结果

- 快照版本会部署到：`http://172.17.17.161:8081/repository/xzh-snapshots/`
- 正式版本会部署到：`http://172.17.17.161:8081/repository/xzh-release/`