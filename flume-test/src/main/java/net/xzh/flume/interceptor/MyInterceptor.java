package net.xzh.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的Host拦截器
 */
public class MyInterceptor implements Interceptor {

    //声明一个存放拦截器处理后事件的集合
    private List<Event> addHeaderEvents;
    // 使用日志
    private static final Logger logger = LoggerFactory
            .getLogger(MyInterceptor.class);

    // 初始化操作
    public void initialize() {
        //初始化存放事件的集合
        addHeaderEvents = new ArrayList<Event>();
    }

    // 单个事件拦截器
    public Event intercept(Event event) {
        // 对事件做处理，事件包含消息体和头部
        //1.获取事件中的头信息
        Map<String, String> headers = event.getHeaders();
        //2.获取事件中的 body 信息
        String body = new String(event.getBody());
        //3.根据 body 中是否有"atguigu"来决定添加怎样的头信息
        if (body.contains("xzh")) {
            //4.添加头信息
            headers.put("from", "xzh");
        } else {
            //4.添加头信息
            headers.put("from", "other");
        }
        return event;
    }

    // 处理所有事件
    public List<Event> intercept(List<Event> events) {
        List<Event> eventList = new ArrayList<Event>();
        for (Event event : events) {
            Event event1 = intercept(event);
            if (event1 != null) {
                eventList.add(event1);
            }
        }
        return eventList;
    }

    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        public Interceptor build() {
            return new MyInterceptor();
        }

        // 初始化配置
        public void configure(Context context) {

        }
    }
}
