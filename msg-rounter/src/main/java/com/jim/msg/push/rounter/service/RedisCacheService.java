package com.jim.msg.push.rounter.service;


import com.jim.msg.push.rounter.entity.ExpiredKey;
import com.jim.msg.push.rounter.entity.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService<V> {

    private Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

    private int i = 0;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,V> redisTemplate;

    public boolean cache(ExpiredKey key, V object){
        try {
            String keyStr = key.getFullKey();
            if(key.getTimeUnit() == null){
                redisTemplate.opsForValue().set(keyStr,object);
            }else{
                redisTemplate.opsForValue().set(keyStr,object,key.getExpiredMillis(), TimeUnit.MILLISECONDS);
            }
            return true;
        }catch (Exception e){
            logger.error("缓存操作失败：" ,e);
        }
        return false;
    }

    public V get(Key key){
        return redisTemplate.opsForValue().get(key.getFullKey());
    }

    public void lpush(ExpiredKey key,Object o){
        try {
            DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
            ClassPathResource resource = new ClassPathResource("refreshToken.lua");
            script.setScriptSource(new ResourceScriptSource(resource));
            script.setResultType(Boolean.class);
            redisTemplate.execute(script, Arrays.asList(key.getFullKey()),o,key.getExpiredSeconds());
        }catch (Exception e){
            logger.error("",e);
        }
    }

    public Boolean updateTime(ExpiredKey key){
        return redisTemplate.expire(key.getFullKey(),key.getDuration(),key.getTimeUnit());
    }

    public Boolean remove(Key key){
        return redisTemplate.delete(key.getFullKey());
    }
}
