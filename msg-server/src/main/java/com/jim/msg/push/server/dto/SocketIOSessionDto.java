package com.jim.msg.push.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 12:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketIOSessionDto implements Serializable {
    private String sessionId;
    private String userName;
    private String consumerQueue;
}

