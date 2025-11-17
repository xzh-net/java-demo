# Pulsar 2.10.1

## 1. 自定义function

### 1.1 编译上传

将编译后文件上传到服务器的`/opt/apache-pulsar-2.10.1/examples`路径下

```bash
mvn clean package
```

### 1.2 创建function

```bash
cd /opt/apache-pulsar-2.10.1
bin/pulsar-admin functions create \
--jar examples/pulsar-1.0-SNAPSHOT.jar \
--classname net.xzh.pulsar.functions.FormatDateFunction \
--inputs persistent://public/default/test_input \
--output persistent://public/default/test_output \
--tenant public \
--namespace default \
--name FormatDateTest
```

### 1.3 触发测试

```bash
bin/pulsar-admin functions trigger --name FormatDateTest --trigger-value "2022/09/13 23/12/30"
```

### 1.4 启动消费者

```bash
cd /opt/apache-pulsar-2.10.1
bin/pulsar-client consume persistent://public/default/test_output -s 'test'
```

### 1.5 发送测试消息

```bash
cd /opt/apache-pulsar-2.10.1
bin/pulsar-client produce persistent://public/default/test_input --messages '2022/09/13 23/12/30'
```


## 2. Connector


### 2.1 Flink从Pulsar中读取消息的操作

net.xzh.pulsar.connector.flink.FlinkFromPulsarSource

```bash
cd /opt/apache-pulsar-2.10.1/bin
# 创建租户
./pulsar-admin tenants create my-tenant
# 创建命名空间
./pulsar-admin namespaces create my-tenant/test-namespace
# 创建一个没有分区的topic
./pulsar-admin topics create persistent://my-tenant/test-namespace/my-topic
# 生产消息
./pulsar-client produce persistent://my-tenant/test-namespace/my-topic --messages "flink-pulsar" --num-messages 0
```

### 2.2 Flink将监听到数据导入Pulsar

net.xzh.pulsar.connector.flink.FlinkFromPulsarSink

```bash
# 服务器安装
yum install -y nc
nc -lk 44444	# node01打开监听端口

cd /opt/apache-pulsar-2.10.1/bin
# 启动监听者
./pulsar-client consume persistent://my-tenant/test-namespace/my-topic -s "first-sub" --num-messages 0	
```

### 2.3 基于Flink实现读取一个POJO类型的数据, 再数据写入到Pulsar中

向主题`my-topic3`生产信息：net.xzh.pulsar.consumer.PulsarProducerSchemaTest

获取`my-topic3`的信息转发到`my-topic4`主题：net.xzh.pulsar.connector.flink.FlinkFromPulsarSchema

监听`my-topic4`信息：net.xzh.pulsar.consumer.PulsarConsumerTest

```bash
cd /opt/apache-pulsar-2.10.1/bin
# 如果消费失败可能是主题之前存在模型，需要重新创建主题
./pulsar-admin topics create persistent://my-tenant/test-namespace/my-topic3
./pulsar-admin topics create persistent://my-tenant/test-namespace/my-topic4
```

