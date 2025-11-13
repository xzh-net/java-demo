package net.xzh.flume.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

/*
自定义Source
 */
public class MySource extends AbstractSource implements Configurable, PollableSource {

    private String prefix;
    private String subfix;
    private Long delay;

    public void configure(Context context) {
        prefix = context.getString("prefix", "pre-");
        subfix = context.getString("subfix");
        delay = context.getLong("delay");
    }

    // 处理数据
    public Status process() throws EventDeliveryException {
        Status status = null;
        try {
            // 自己模拟数据来发送
            for (int i = 0; i < 10; i++) {
                Event event = new SimpleEvent();
                event.setBody((prefix + i + subfix).getBytes());
                // 将数据存储到与Source关联的Channel中
                getChannelProcessor().processEvent(event);
                // 数据准备消费
                status = Status.READY;
            }
            // 休眠n秒,每n秒发送10条数据
            Thread.sleep(delay);
        } catch (Exception e) {
            // 打印日志
            e.printStackTrace();
            status = Status.BACKOFF;
        }
        return status;
    }

    public long getBackOffSleepIncrement() {
        return 0;
    }

    public long getMaxBackOffSleepInterval() {
        return 0;
    }


}
