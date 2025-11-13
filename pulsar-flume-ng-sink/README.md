# Flume 发送日志数据至 Apache Pulsar

## 环境安装


- [Flume 1.9.0](https://www.xuzhihao.net/#/deploy/flume)


- Pulsar 2.8.4

```bash
docker run -dit \
    -p 6650:6650 \
    -p 8080:8080 \
    -v pulsardata:/data/pulsar/data \
    -v pulsarconf:/data/pulsar/conf \
    --name pulsar-standalone \
    apachepulsar/pulsar:2.8.4 \
    bin/pulsar standalone
    
# 进入容器    
docker exec -it pulsar-standalone /bin/bash
# 创建主题
bin/pulsar-admin topics create persistent://public/default/test
# 发送消息
bin/pulsar-client produce persistent://public/default/test --messages "admin123456789"
# 实时消费消息（持续监听）
bin/pulsar-client consume persistent://public/default/test -s "my-subscription" -n 0
# 查询主题列表
bin/pulsar-admin topics list public/default
# 删除主题
bin/pulsar-admin topics delete persistent://public/default/test
```

## 编译上传

编译

```bash
mvn package
```

上传

```bash
cp /opt/software/flume-ng-pulsar-sink-1.9.0.jar /opt/flume/lib/
```

## 创建flume-netcat-pulsar.conf文件

```bash
cd /opt/flume/job
vim flume-netcat-pulsar.conf
```

```conf
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source
a1.sources.r1.type = netcat
a1.sources.r1.bind = localhost
a1.sources.r1.port = 44444

# Describe the sink
a1.sinks.k1.type = org.apache.flume.sink.pulsar.PulsarSink
a1.sinks.k1.serviceUrl = node01.xuzhihao.net:6650,node02.xuzhihao.net:6650,node03.xuzhihao.net:6650
a1.sinks.k1.topicName = test
a1.sinks.k1.producerName = testProducer
# Use a channel which buffers events in memory
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 1000

# Bind the source and sink to the channel
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1

```

## 启动服务

```bash
cd /opt/flume/
bin/flume-ng agent --conf conf/ --name a1 --conf-file job/flume-netcat-pulsar.conf
```

## 发送数据

```bash
nc localhost 44444
```