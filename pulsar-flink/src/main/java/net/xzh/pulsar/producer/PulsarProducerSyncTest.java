package net.xzh.pulsar.producer;


import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;

// 演示Pulsar 生产者 同步发送
public class PulsarProducerSyncTest {
    public static void main(String[] args) throws Exception {

        //1. 创建Pulsar的客户端对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node01:6650,node02:6650,node03:6650").build();

        //2. 通过客户端创建生产者的对象

        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                .topic("persistent://my-tenant/test-namespace/my-topic1")
                .create();
        //3. 使用生产者发送数据
        producer.send("hello java API pulsar ...");

        System.out.println("数据生产完成....");
        //4. 释放资源
        producer.close();
        pulsarClient.close();


    }
}
