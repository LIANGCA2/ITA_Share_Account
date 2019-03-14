package com.oocl.ita.demo.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RestRequestUtilTest {
    @Test
    public void should_test_get() {
        //Given
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx43a6e59333e94db9&secret=123&js_code=123&grant_type=authorization_code";

        //When
        String result = RestRequestUtil.get(url);

        //Then
        Assert.assertNotNull(result);
    }
}