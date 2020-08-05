package com.jim.msg.push.server.common;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 11:19
 */
public class SocketIOConst {
    public static final  Integer SESSION_TIME_OUT_SECOND = 60*60*7;

    public interface Req{
        interface Event{
            String SEND_MSG_EVENT = "sendMsg";
        }
    }
}
