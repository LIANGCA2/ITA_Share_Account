package com.oocl.ita.demo.util;

import java.util.UUID;

public class StatusUtil {
    public static String getLoginStatus(String openId, String sessionKey){
        return UUID.nameUUIDFromBytes((openId + sessionKey).getBytes()).toString();
    }
}
