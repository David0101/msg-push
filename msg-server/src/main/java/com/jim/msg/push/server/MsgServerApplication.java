package com.jim.msg.push.server;

import com.jim.msg.push.commons.listener.ApplicationPortFileWriter;
import com.jim.msg.push.rounter.commons.SocketIOHelper;
import com.jim.msg.push.rounter.mq.WebSocketDisconnectMsgSend;
import com.jim.msg.push.rounter.mq.WebSocketMsgRedirectSend;
import lombok.extern.slf4j.Slf4j;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-07-31 11:30
 */
//@ComponentScan({"com.jim.msg.push.rounter.config","com.jim.msg.push.server"})
@SpringBootApplication
@Slf4j
//@EnableTransactionManagement
//@MapperScan({"com.jim.msg.push.server.mapper"})
@EnableFeignClients(basePackages = {"com.jim.msg.push"})
@EnableDiscoveryClient
//@EnableHystrix
@EnableScheduling
@EnableAsync
@Import({SocketIOHelper.class, WebSocketDisconnectMsgSend.class, WebSocketMsgRedirectSend.class})

public class MsgServerApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsgServerApplication.class);

        app.addListeners(new ApplicationPidFileWriter("./msg-server.pid"),new ApplicationPortFileWriter("./msg-server.port"));
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);

    }
}
