package com.jim.msg.push.auth.utils;

import org.apache.commons.lang3.StringUtils;

public class TokenKeyUtil {
    public static String getScretKeyWithOrgId(String clientId, int appType, String orgId, String username){
        if (StringUtils.isNotBlank(orgId)) {
            return clientId + ":" + appType + ":" + orgId + ":" + username;
        } else {
            return clientId + ":" + appType + ":" + username;
        }
    }
}
