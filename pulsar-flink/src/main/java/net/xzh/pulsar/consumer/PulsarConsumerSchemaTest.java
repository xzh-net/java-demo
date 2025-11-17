package net.xzh.pulsar.consumer;


import net.xzh.pulsar.pojo.User;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.AvroSchema;

// 演示 Pulsar的消费者的使用
public class PulsarConsumerSchemaTest {

    public static void main(String[] args)  throws Exception{

        //1. 创建pulsar的客户端的对象
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://node01:6650,node02:6650,node03:6650").build();

        //2. 基于客户端对象构建消费者对象

        Consumer<User> consumer = pulsarClient.newConsumer(AvroSchema.of(User.class))
                .topic("persistent://my-tenant/test-namespace/my-topic4")
                .subscriptionName("sub4")
                .subscribe();
        //3. 循环读取数据操作

        while(true){
            //3.1: 接收消息
            Message<User> message = consumer.receive();
            //3.2: 获取消息数据
            User msg = message.getValue();
            System.out.println(msg);

        }
    }
}
