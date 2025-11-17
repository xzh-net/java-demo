package net.xzh.flink;

import java.util.Properties;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

public class KafkaSourceSinkSample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //接收源：Kafka Source
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("172.17.17.161:9092")
                .setTopics("source-text")
                .setGroupId("consumer-group")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        DataStreamSource<String> dataStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        //转换：将输入内容按空格切割，发送给后续
        SingleOutputStreamOperator<String> flatMap = dataStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] words = s.split(" ");
                for (int i = 0; i < words.length; i++) {
                    collector.collect(words[i]);
                }
            }
        });

        //输出（Sink），向Kafka targets-words写入每一个单词
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "172.17.17.161:9092");
        flatMap.addSink(new FlinkKafkaProducer<String>(
                "target-words",
                new SimpleStringSchema(),
                properties
        ));


        env.execute();
    }
}

