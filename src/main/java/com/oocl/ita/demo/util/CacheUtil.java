package com.oocl.ita.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtil {
    private static CacheUtil Instance = null;
    private CacheUtil(){
        lastRefreshTime = System.currentTimeMillis();
        sessionCache = new HashMap<>();
    }

    public static CacheUtil getInstance(){
        if(Instance == null){
            Instance = new CacheUtil();
        }
        return Instance;
    }

    private Map<String, String> sessionCache;

    private static String splitter = "#";
    private static long lastRefreshTime = 0;
    private static long refreshInterval = 1000 * 60 * 5; //  5 min

    public String getOpenId(String loginStatus) {
        String value = sessionCache.get(loginStatus);
        if(value == null) {
            return null;
        }
        return getOpenIdFromValue(value);
    }

    private String getOpenIdFromValue(String cacheValue) {
        try{
            return cacheValue.split(splitter)[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String get(String loginStatus) {
        String value = sessionCache.get(loginStatus);
        if(value == null) {
            return null;
        }
        return removeTime(value);
    }

    private String removeTime(String cacheValue) {
        try {
            String[] values = cacheValue.split(splitter);
            String openId = values[1];
            String sessionKey = values[2];
            return openId + splitter + sessionKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void put(String loginStatus, String openId, String sessionKey) {
        refresh();
        sessionCache.put(loginStatus, buildCacheValue(openId, sessionKey));
    }

    private String buildCacheValue(String openId, String sessionKey) {
        return System.currentTimeMillis() + splitter + openId + splitter + sessionKey;
    }

    private void refresh(){
        long timeNow = System.currentTimeMillis();
        if(lastRefreshTime + refreshInterval > timeNow) {
            clearCache(timeNow);
            lastRefreshTime = timeNow;
        }
    }

    private void clearCache(long timeNow) {
        List<String> idToRemove = new ArrayList<>();
        for(Map.Entry<String, String> entry : sessionCache.entrySet()) {
            long updateTime = Long.valueOf(entry.getValue().split(splitter)[0]);
            if(updateTime + refreshInterval > timeNow) {
                idToRemove.add(entry.getKey());
            }
        }
        for(String id : idToRemove){
            sessionCache.remove(id);
        }
    }
}
