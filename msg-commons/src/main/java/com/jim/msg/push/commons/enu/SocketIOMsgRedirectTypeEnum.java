package com.jim.msg.push.commons.enu;

import lombok.Getter;

/**
 * @author Ming
 * @date 2020/3/20 11:04
 */
@Getter
public enum SocketIOMsgRedirectTypeEnum {
    PERSONAL("PERSONAL","个人"),
    ROOM("ROOM","房间"),
    ALL("ALL","所有");

    private String value;
    private String name;

    SocketIOMsgRedirectTypeEnum(String value, String name){
        this.value = value;
        this.name = name;
    }



}
