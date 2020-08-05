package com.jim.msg.push.rounter;

import com.jim.msg.push.rounter.commons.SocketIOHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 12:20
 */
@Slf4j
@SpringBootApplication
public class RounterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RounterApplication.class,args);
        log.info("RounterApplication start");
    }
}
