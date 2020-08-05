package com.jim.msg.push.server.DTO;

import lombok.Data;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 11:35
 */

@Data
public class MessageDto {
    /**
     * 源客户端用户名
     */
    private String sourceUserName;

    /**
     * 目标客户端用户名
     */
    private String targetUserName;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String msgContent;
}
