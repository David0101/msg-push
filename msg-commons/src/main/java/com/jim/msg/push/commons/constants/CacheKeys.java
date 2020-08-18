package com.jim.msg.push.commons.constants;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 14:14
 */
public class CacheKeys {
    public static final String USER_SOCKET_CLIENT_SESSION = "user:socket:client:session:";
    public static final String USER_SOCKET_CLIENT_USER = "user:socketClient:user:";
    public static final String USER_MSG_MAP = "user:msg:map";

    public interface ExpireTime{
        Integer USER_MSG_MAP_SECOND = 60 * 60 * 3;
    }
}
