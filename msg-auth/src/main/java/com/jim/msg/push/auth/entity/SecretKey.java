package com.jim.msg.push.auth.entity;

import java.util.concurrent.TimeUnit;

public class SecretKey extends ExpiredKey{

    private static final String SECRET_PREFIX = "st:";

    public SecretKey(String key, long duration, TimeUnit timeUnit) {
        super(SECRET_PREFIX,key, duration, timeUnit);
    }


    public SecretKey(String key) {
        super(SECRET_PREFIX,key, 5, TimeUnit.MINUTES);
    }

}