package com.jim.msg.push.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-18 16:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketIOMsgDto implements Serializable {
    public static String DELIMITER = ",";
    private String id;
    private String message;
    private String event;
    private String userId;
    private String platform;


}
