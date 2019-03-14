package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.repositories.AccountRepository;
import com.oocl.ita.demo.repositories.UserRepository;
import com.oocl.ita.demo.services.impl.LoginServiceImpl;
import com.oocl.ita.demo.util.CacheUtil;
import com.oocl.ita.demo.util.RestRequestUtil;
import com.oocl.ita.demo.util.StatusUtil;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static mockit.Deencapsulation.invoke;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JMockit.class)
public class LoginServiceTest {
    @Tested
    private LoginServiceImpl loginService;

    @Injectable
    private UserRepository userRepository;

    @Injectable
    private TypeService typeService;

    @Mocked
    private CacheUtil cacheUtil;

    @Test
    public void should_return_login_when_call_checkLogin_given_loginStatus_valid() {
        new Expectations(CacheUtil.class) {{
            cacheUtil.get("login");
            result = "success";
        }};
//        new MockUp<CacheUtil>(){
//            @Mock
//            public String get(String loginStatus){
//                return "success";
//            }
//        };
        String loginStatus = "login";
        boolean result = loginService.checkLogin(loginStatus);

        assertTrue(result);
    }

    @Test
    public void should_return_openId_when_call_getOpenId_given_valid_loginStatus() {
        // given
        new Expectations(CacheUtil.class) {{
            cacheUtil.getOpenId("login");
            result = "open id";
        }};
        String loginStatus = "login";
        // when
        String result = loginService.getOpenId(loginStatus);
        // then
        assertEquals("open id", result);
    }

    @Test
    public void should_return_false_when_call_setDate_given_user_list_empty() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        List<User> users = new ArrayList<>();
        // when
        Method method = LoginServiceImpl.class.getDeclaredMethod("setDate", List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(loginService, users);
        // then
        assertFalse(result);
    }

    @Test
    public void should_return_true_when_call_setDate_given_user_list_not_empty() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        List<User> users = new ArrayList<>();
        User user = mock(User.class);
        users.add(user);
        when(user.getDate()).thenReturn(null);
        // when
        Method method = LoginServiceImpl.class.getDeclaredMethod("setDate", List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(loginService, users);
        // then
        assertTrue(result);
    }

    @Test
    public void should_return_error_when_call_weChatLogin_given_code_not_valid() {
        new MockUp<RestRequestUtil>() {
            @Mock
            public String get(String url) {
                return "{\"errcode\":123, \"errmsg\":\"error\"}";
            }
        };
        String result = loginService.weChatLogin("code");
        assertEquals("error#error", result);
    }
    
    @Test
    public void should_return_login_status_when_call_weChatLogin_given_code_valid_and_new_user(){
        new MockUp<RestRequestUtil>() {
            @Mock
            public String get(String url) {
                return "{\"openid\":\"openid\", \"session_key\":\"key\"}";
            }
        };

        new MockUp<StatusUtil>() {
            @Mock
            public String getLoginStatus(String openId, String session_key) {
                return "loginStatus";
            }
        };

        new MockUp<UserRepository>() {
            @Mock
            public List<User> findByUserId(String openId) {
                List<User> users = new ArrayList<>();
                return users;
            }

            @Mock
            public void save(User user) {

            }
        };

        new MockUp<CacheUtil>() {
            @Mock
            public void put(String loginStatus, String openId, String sessionKey){

            }
        };

        String result = loginService.weChatLogin("123");
        assertEquals("loginStatus", result);
    }
}
