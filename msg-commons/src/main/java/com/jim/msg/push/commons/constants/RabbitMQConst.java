package com.jim.msg.push.commons.constants;

public class RabbitMQConst {

    public interface Queue {
        String WEB_SOCKET_QUEUE = "websocket.queue";
    }

    public interface Exchange {
         class Fanout{
             public static final String WEB_SOCKET_DISCONNECT_EXCAHNGE = "websocket.disconnect.exchange";
             public static final String WEB_SOCKET_EXCHANGE = "websocket.exchange";

        }
        class Direct{
            public static final String WEB_SOCKET_MSG_REDIRECT_EXCHANGE = "websocket.msgRedirect.exchange";
        }

    }

    public interface RoutingKey {
        String WEB_SOCKET_MSG_REDIRECT_ALL_QUEUE_ROUTING_KEY = "websocket.msgRedirect.allQueue.routingKey";
    }


}
