package com.jim.msg.push.rounter.commons;

import com.jim.msg.push.commons.constants.CacheKeys;
import com.jim.msg.push.commons.constants.RabbitMQConst;
import com.jim.msg.push.commons.util.JacksonUtil;
import com.jim.msg.push.rounter.dto.SocketIODisconnectMsgSendDto;
import com.jim.msg.push.rounter.dto.SocketIOSessionDto;
import com.jim.msg.push.rounter.mq.WebSocketDisconnectMsgSend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 12:26
 */
@Component
@Slf4j
public class SocketIOHelper {



    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WebSocketDisconnectMsgSend webSocketDisconnectMsgSend;



    //正式服读取shell脚本 配置文件信息
    @Value("${socketio.rabbitmq.consumer.queue}")
    private String localConsumerQueue;

    public String getCachSessionKey(String sessionId){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sessionId)){
            return CacheKeys.USER_SOCKET_CLIENT_SESSION+sessionId;
        }
        return "";
    }

    public String getCacheSessionUserKey(String userId) {
        if (StringUtils.isNotBlank(userId) ) {
            return CacheKeys.USER_SOCKET_CLIENT_USER + "#" + userId;
        }
        return "";
    }

    public String getLocalConsumerQueue() {
        return localConsumerQueue;
    }
    public String getCacheSessionKey(String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            return CacheKeys.USER_SOCKET_CLIENT_SESSION + sessionId;
        }
        return "";
    }
    public SocketIOSessionDto getRedisUser(String sessionId) {
        String sessionKey = this.getCacheSessionKey(sessionId);
        Object sessionJsonValue = redisTemplate.opsForValue().get(sessionKey);
        SocketIOSessionDto socketIOSessionDto = JacksonUtil.string2Obj(sessionJsonValue.toString(),SocketIOSessionDto.class);
        return socketIOSessionDto;
    }
    public void logout(String sessionId) {
        SocketIOSessionDto redisUser = this.getRedisUser(sessionId);
        log.info("用户:{}断开长连接通知 ",
                JacksonUtil.obj2String(redisUser));
        if (redisUser != null) {
            String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DATETIME);
            String disconnectTime = simpleDateFormat.format(new Date());
            SocketIODisconnectMsgSendDto sendDto = new SocketIODisconnectMsgSendDto();
            sendDto.setUserId(redisUser.getUserName());
            sendDto.setDisconnectTime(disconnectTime);
            sendDto.setSessionId(sessionId);
            //获取断线通知
            webSocketDisconnectMsgSend.send(RabbitMQConst.Queue.WEB_SOCKET_QUEUE, sendDto);

            String userKey = this.getCacheSessionUserKey( redisUser.getUserName());
            redisTemplate.opsForHash().delete(userKey,sessionId);//
            String sessionKey = this.getCacheSessionKey(redisUser.getSessionId());
            redisTemplate.delete(sessionKey);
        }
    }
    public void resetSessionTime(String sessionId) {
        try {
            SocketIOSessionDto redisUser = this.getRedisUser(sessionId);
            if (redisUser != null) {
                String userKey = this.getCacheSessionUserKey( redisUser.getUserName());
                redisTemplate.expire(userKey,SocketIOConst.SESSION_TIME_OUT_SECOND, TimeUnit.SECONDS);
                String cacheSessionKey = this.getCacheSessionKey(sessionId);
                redisTemplate.expire(cacheSessionKey,SocketIOConst.SESSION_TIME_OUT_SECOND,TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("resetSessionTimeException:", e);
        }

    }
}
