# 基于Flink+Kafka通用数据处理流程

向`source-text`主题发送消息，Flink监听消息处理后转发到`target-words`主题

## 安装环境

```bash
docker run -d -p 2181:2181 --name some-zookeeper --restart always -d zookeeper:3.7.0

docker run -itd --name kafka -p 9092:9092 \
  --link some-zookeeper:zookeeper \
  -e KAFKA_BROKER_ID=0 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.17.17.161:9092 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  -t wurstmeister/kafka:2.13-2.8.1
```

## 客户端测试

```bash
docker exec -it kafka /bin/bash
cd /opt/kafka_2.13-2.8.1
# 创建主题
bin/kafka-topics.sh --create --topic source-text --partitions 2 --replication-factor 1 --bootstrap-server 172.17.17.161:9092
bin/kafka-topics.sh --create --topic target-words --partitions 2 --replication-factor 1 --bootstrap-server 172.17.17.161:9092
# 发送消息
bin/kafka-console-producer.sh --topic source-text --bootstrap-server 172.17.17.161:9092
# 消费
bin/kafka-console-consumer.sh --topic target-words --from-beginning --bootstrap-server 172.17.17.161:9092
# 查询所有主题
bin/kafka-topics.sh --list --bootstrap-server 172.17.17.161:9092
# 删除主题
bin/kafka-topics.sh --delete --topic topic1 --bootstrap-server 172.17.17.161:9092
```