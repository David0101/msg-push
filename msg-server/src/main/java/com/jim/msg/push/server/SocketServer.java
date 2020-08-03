package com.jim.msg.push.server;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-03 15:16
 */
@Slf4j
@Component
@Order(1)
public class SocketServer implements CommandLineRunner {

    @Autowired
    private SocketIOServer socketIOServer;

    @Override
    public void run(String... args) {
        log.info("---------- NettySocket通知服务开始启动 ----------");
        socketIOServer.start();
        log.info("---------- NettySocket通知服务启动成功 ----------");
    }
}
