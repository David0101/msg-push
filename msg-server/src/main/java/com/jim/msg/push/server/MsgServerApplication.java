package com.jim.msg.push.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-07-31 11:30
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
//@MapperScan({".mapper"})
//@EnableFeignClients(basePackages = {"com."})
@EnableDiscoveryClient
@EnableHystrix
@EnableScheduling
@EnableAsync
public class MsgServerApplication {
}
