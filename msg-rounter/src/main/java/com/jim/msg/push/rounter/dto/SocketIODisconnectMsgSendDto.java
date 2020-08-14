package com.jim.msg.push.rounter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 断线MQ通知(用于SOCKETIO断线之后通知想做业务处理的系统)
 *
 * @author lianxinxiong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketIODisconnectMsgSendDto implements Serializable {
    /**
     * 消息（实体用JSON封装，再解析）
     */
    private String userId;

    /**
     * 下线时间
     */
    private String disconnectTime;

    /**
     * 用户类型；
     */
    private Integer userType;

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台
     */
    private String sessionId;

}
