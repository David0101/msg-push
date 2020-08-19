package com.jim.msg.push.rounter.commons;

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
    public interface Rsp {
        interface Msg {
            Integer SERVER_DEFAULT_SUCCESS_RESPONSE = 200;
        }

        interface Event {
            String SERVER_LOGIN_RSP_EVENT = "serverLoginRspEvent";
            String ERROR_EVENT = "errorEvent";


            String GET_MSG_EVEN = "get_msg_even";
        }

    }
}
