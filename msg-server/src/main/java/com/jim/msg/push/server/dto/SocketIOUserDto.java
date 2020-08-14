package com.jim.msg.push.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-13 18:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketIOUserDto  implements Serializable {

    private static final long serialVersionUID = 245408918020086603L;
    private String sessionId;
    private String consumerQueue;
}
