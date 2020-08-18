package com.jim.msg.push.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 11:35
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    private String sourceUserName;

    private String targetUserName;

    private String msgType;

    private String msgContent;
}
