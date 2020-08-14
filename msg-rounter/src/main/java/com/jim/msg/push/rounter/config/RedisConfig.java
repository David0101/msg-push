package com.jim.msg.push.rounter.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jim.msg.push.rounter.cache.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 14:48
 */

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

    @Value("${redis.pool.maxTotal}")
    private int maxTotal;
    @Value("${redis.pool.minIdle}")
    private int minIdle;
    @Value("${redis.pool.maxWaitMillis}")
    private int maxWaitMillis;




    @Bean
    @ConfigurationProperties()
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return new JedisPoolConfig();

    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration(){
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .usePooling().poolConfig(jedisPoolConfig()).build();
        return jedisClientConfiguration;
    }


    @Bean
    @ConfigurationProperties()
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setPassword(password);
        redisConfiguration.setHostName(server);
        redisConfiguration.setDatabase(database);
        redisConfiguration.setPort(port);
        return redisConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration RedisStandaloneConfiguration){
        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(RedisStandaloneConfiguration);
        return redisConnectionFactory;
    }

    @Bean("redisTemplate")
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;

    }
}
