package com.jim.msg.push.rounter.entity;

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
