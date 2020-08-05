package com.jim.msg.push.rounter.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 12:26
 */
@Component
@Slf4j
public class SocketIOHelper {
    //读取shell脚本 配置文件信息
    @Value("${socketio.rabbitmq.consumer.queue}")
    private String localConsumerQueue;

    public String getLocalConsumerQueue() {
        return localConsumerQueue;
    }
}
