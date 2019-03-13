package com.oocl.ita.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import net.minidev.json.JSONObject;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 12, 2019</pre>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private LoginService loginService;


    @Test
    public void should_get_UserInfo_when_call_getUserInfo_given_trd_session() throws Exception {
        // given
        UserInfo userInfo = new UserInfo();
        userInfo.setRecords("records");
        String trd_session = "test";
        String open_id = "user id";
        when(loginService.getOpenId(trd_session)).thenReturn(open_id);
        when(userService.getUserInfo(open_id)).thenReturn(userInfo);

        // when
        // then
        mockMvc.perform(get("/api/v1/users?trd_session=" + trd_session).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.records", is("records")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_status_401_when_call_getUserInfo_given_user_not_login() throws Exception {
        // given
        String trd_session = "test";
        when(loginService.getOpenId(trd_session)).thenReturn(null);

        // when
        // then
        mockMvc.perform(get("/api/v1/users?trd_session=" + trd_session).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_return_status_NO_CONTENT_when_call_updateUserInfo_given_trd_session_user() throws Exception {
        //Given
        String trd_session = "test";
        User user = new User();
        Map map = Maps.newHashMap("",user);
        String requestJson = JSONObject.toJSONString(map);
        String open_id = "user id";
        when(loginService.getOpenId(trd_session)).thenReturn(open_id);
        doNothing().when(userService).updateUserInfo(open_id, user);

        //When
        //Then
        mockMvc.perform(post("/api/v1/users?trd_session=" + trd_session).content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_status_UNAUTHORIZEDT_when_call_updateUserInfo_given_trd_session_is_null() throws Exception {
        //Given
        String trd_session = "";
        User user = new User();
        Map map = Maps.newHashMap("",user);
        String requestJson = JSONObject.toJSONString(map);
        String open_id = "user id";
        when(loginService.getOpenId(trd_session)).thenReturn(null);
        doNothing().when(userService).updateUserInfo(open_id, user);

        //When
        //Then
        mockMvc.perform(post("/api/v1/users?trd_session=" + trd_session).content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
