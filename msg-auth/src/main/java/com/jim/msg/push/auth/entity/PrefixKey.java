package com.jim.msg.push.auth.entity;

import lombok.Data;

@Data
public class PrefixKey extends Key {
    private String prefix;

    public PrefixKey(String prefix,String key){
        super(key);
        this.prefix = prefix;
    }

    public PrefixKey(String key){
        super(key);
        this.prefix = "";
    }

    public String getFullKey(){
        return prefix + getKey();
    }
}
