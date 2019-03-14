package com.oocl.ita.demo.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.exceptions.BadRequestException;
import com.oocl.ita.demo.factory.AccountFactory;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.AccountService;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import net.minidev.json.JSONObject;
import org.assertj.core.util.Maps;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTestV2 {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private LoginService loginService;
    @MockBean
    private AccountService accountService;


    @Test
    public void should_return_status_ok_when_call_addAccount_given_account() throws Exception {
        //Given
        String trd_session = "test";
        String open_id = "user id";
        User user = mock(User.class);
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");
        when(loginService.getOpenId(trd_session)).thenReturn(open_id);
        when(userService.findUserByUserId(open_id)).thenReturn(user);
        Map map = Maps.newHashMap("",account);
        doNothing().when(accountService).save(account, user);

        //When
        //Then
        mockMvc.perform(post("/api/v1/accounts?trd_session=" + trd_session).content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void should_return_No_Content_when_call_deleteAccount_given_id() throws Exception {
        //Given
        Integer id = 1;
        when(accountService.deleteAccount(id)).thenReturn(true);

        //When
        //Then
        mockMvc.perform(delete("/api/v1/accounts/"+id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_Bad_Request_when_call_deleteAccount_given_not_exist_id() throws Exception {
        //Given
        Integer id = 2;
        when(accountService.deleteAccount(id)).thenReturn(false);

        //When
        //Then
        mockMvc.perform(delete("/api/v1/accounts/"+id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_null_when_call_getAccountById_given_not_exist_id() throws Exception{
        //given
        Integer id = 2;
        when(accountService.getAccountById(id)).thenReturn(null);
        //when
        //then
        mockMvc.perform(get("/api/v1/accounts/" + id).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_badRequest_when_call_updateAccountById_given_not_exist_trd_session() throws Exception{
        //given
        Integer id = 2;
        String trd_session = "error_test";
        String openId = null;
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");
        when(loginService.getOpenId(trd_session)).thenReturn(null);
        when(userService.findUserByUserId(openId)).thenReturn(null);
        Map map = Maps.newHashMap("",account);
        //when
        //then
        mockMvc.perform(post("/api/v1/accounts/" + id + "?trd_session=" + trd_session).content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_noContent_when_call_updateAccountById_given_correct_param() throws Exception{
        //given
        Integer id = 2;
        String trd_session = "error_test";
        String openId = null;
        User user = mock(User.class);
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");

        when(loginService.getOpenId(trd_session)).thenReturn(null);
        when(userService.findUserByUserId(openId)).thenReturn(user);
        Map map = Maps.newHashMap("",account);
        when(accountService.updateAccountById(anyInt(), any())).thenReturn(true);


        //when
        //then
        mockMvc.perform(post("/api/v1/accounts/" + id + "?trd_session=" + trd_session).content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_exception_when_call_updateAccountById_given_incorrect_param() throws Exception{
        //given
        Integer id = 2;
        String trd_session = "error_test";
        String openId = null;
        User user = mock(User.class);
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");

        when(loginService.getOpenId(trd_session)).thenReturn(null);
        when(userService.findUserByUserId(openId)).thenReturn(user);
        Map map = Maps.newHashMap("",account);
        when(accountService.updateAccountById(anyInt(), any())).thenReturn(false);


        //when
        //then
        mockMvc.perform(post("/api/v1/accounts/" + id + "?trd_session=" + trd_session).content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
