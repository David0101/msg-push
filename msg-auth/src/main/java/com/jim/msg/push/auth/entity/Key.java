package com.jim.msg.push.auth.entity;

import lombok.Data;

@Data
public class Key {
    private String key;

    public Key(String key){
        this.key = key;
    }

    public String getFullKey(){
        return key;
    }
}
