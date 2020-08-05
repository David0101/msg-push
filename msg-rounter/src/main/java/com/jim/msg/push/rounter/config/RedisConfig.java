package com.jim.msg.push.rounter.config;

import com.jim.msg.push.rounter.cache.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 14:48
 */
@Component
@Lazy(value = false)
@Configuration
public class RedisConfig {
    private static  final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${redis.server}")
    private String server;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.database}")
    private int database;

    @PostConstruct
    public void init() {
        initRedis();
    }

    private void initRedis() {
        LOG.info("----------init redis start-----");
        RedisClient.init(server, port, password,database);
        LOG.info("----------init redis end-----");
    }

}
