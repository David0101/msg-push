package com.jim.msg.push.rounter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-18 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketIOMsgRedirectDto implements Serializable {
    private String redirectSendType;
    private String room;
    private String sessionId;
    private String event;
    private Object message;




}