package com.jim.msg.push.rounter.entity;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class ExpiredKey extends PrefixKey{

    private long duration;

    private TimeUnit timeUnit;

    private long currentTime;

    public ExpiredKey(String key, long duration, TimeUnit timeUnit){
        super(key);
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    public ExpiredKey(String prefix, String key){
        super(prefix,key);
    }

    public ExpiredKey(String key){
        super(key);
    }

    public ExpiredKey(String prefix, String key, long duration, TimeUnit timeUnit){
        super(prefix,key);
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    public long getExpiredMillis(){
        return timeUnit.toMillis(duration);
    }

    public long getExpiredSeconds(){
        return timeUnit.toSeconds(duration);
    }

    private Date getExpiredTime(){

        return new Date(currentTime + timeUnit.toMillis(duration));
    }

}
