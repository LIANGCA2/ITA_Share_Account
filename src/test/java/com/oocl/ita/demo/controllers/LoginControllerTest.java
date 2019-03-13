package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.services.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    public void should_return_login_when_call_wechatLogin_given_trd_session_valid() throws Exception {
        String trd_session = "login_status";
        when(loginService.checkLogin(trd_session)).thenReturn(true);

        mockMvc.perform(get("/api/v1/login?trd_session=" + trd_session).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.msg", is("Login")))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void should_return_trd_session_when_call_wechatLogin_given_code_valid() throws Exception {
        String code = "code";
        String trd_session = "login_Status";
        when(loginService.weChatLogin(code)).thenReturn(trd_session);

        mockMvc.perform(get("/api/v1/login?code=" + code).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.obj.trd_session", is(trd_session)))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void should_return_error_msg_when_call_wechatLogin_given_code_not_valid() throws Exception {
        String code = "code";
        String error = "error#1234";
        String error_msg = "1234";
        when(loginService.weChatLogin(code)).thenReturn(error);

        mockMvc.perform(get("/api/v1/login?code=" + code).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.obj.error_msg", is(error_msg)))
                .andExpect(jsonPath("$.success", is(false)));
    }
}
