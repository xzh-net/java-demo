package net.xzh.pulsar.connector.flink;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.pulsar.FlinkPulsarSink;
import org.apache.flink.streaming.util.serialization.PulsarPrimitiveSchema;

import java.util.Optional;
import java.util.Properties;

/**
 * 将监听到数据导入Pulsar
 */
public class FlinkFromPulsarSink {

    public static void main(String[] args) throws Exception {

        //1. 创建flink的流式处理的核心类对象
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //2. 添加source组件: 监控某一个端口号, 从端口号读取数据操作
        DataStreamSource<String> streamSource = env.socketTextStream("node01", 44444);

        //3. 添加转换的组件

        //4. 添加sink的组件:  如何将处理后的数据输出到Pulsar中
        FlinkPulsarSink<String> pulsarSink = new FlinkPulsarSink<String>(
                "pulsar://node01:6650,node02:6650,node03:6650",
                "http://node01:8080,node2:8080,node03:8080",
                Optional.of("persistent://my-tenant/test-namespace/my-topic"),
                new Properties(),
                new PulsarPrimitiveSchema<>(String.class)
        );

        streamSource.addSink(pulsarSink);

        //5. 启动flink程序
        env.execute("flinkPulsarSink");
    }
}
