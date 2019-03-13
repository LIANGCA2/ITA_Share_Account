package com.oocl.ita.demo.util;

import org.springframework.web.client.RestTemplate;

public class RestRequestUtil {
    public static String get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
