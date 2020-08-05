package com.jim.msg.push.rounter.commons;

import com.jim.msg.push.commons.constants.CacheKeys;
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

    public String getCachSessionKey(String sessionId){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sessionId)){
            return CacheKeys.USER_SOCKET_CLIENT_SESSION+sessionId;
        }
        return "";
    }
    public String getLocalConsumerQueue() {
        return localConsumerQueue;
    }
}
