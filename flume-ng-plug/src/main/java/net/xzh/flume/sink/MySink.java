package net.xzh.flume.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MySink extends AbstractSink implements Configurable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySink.class);
    // 处理数据
    public Status process() throws EventDeliveryException {
        Status status = null;
        // 获取跟Sink绑定的Channel
        Channel ch = getChannel();
        // 获取事务
        Transaction transaction = ch.getTransaction();
        try {
            // 开启事务
            transaction.begin();
            // 从Channel中接收数据
            Event event = ch.take();
            if(event == null){
                status = Status.BACKOFF;
            }else{
                // 可以将数据发送到外部存储
                // 模拟实现LoggerSink的功能
                LOGGER.info(new String(event.getBody()));
                status = Status.READY;
            }
            // 提交事务
            transaction.commit();
        }catch (Exception e){
            // 打印异常
            LOGGER.error(e.getMessage());
            // 返回失败状态
            status = Status.BACKOFF;
        }finally {
            // 关闭事务
            transaction.close();
        }
        return status;
    }

    public void configure(Context context) {

    }
}
