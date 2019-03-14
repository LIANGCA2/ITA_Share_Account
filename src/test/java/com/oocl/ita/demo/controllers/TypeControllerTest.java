package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.TypeService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TypeController.class)
public class TypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private LoginService loginService;

    @MockBean
    private TypeService typeService;
    @Test
    public void should_get_AllIncomeTypes_when_call_getAllIncomeType_with_user_exist() throws Exception {
        //given
        String type="income";
        when(typeService.getAllTypes(type)).thenReturn(null);
        //when
        //then
        mockMvc.perform(get("/api/v1/type/"+type).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOutlayTypes() throws Exception {
        //given
        String type="outlay";
        when(typeService.getAllTypes(type)).thenReturn(null);
        //when
        //then
        mockMvc.perform(get("/api/v1/type/"+type).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_AllTypes_when_call_getAllTypes_with_user() throws Exception {
        //given
        User user = new User();
        user.setUserId("userid");
        String trd_session = "test";
        String open_id = "user id";
        when(loginService.getOpenId(trd_session)).thenReturn(open_id);
        when(userService.findUserByUserId(open_id)).thenReturn(user);
        //when
        //then
        mockMvc.perform(get("/api/v1/type/"+"?trd_session="+trd_session).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void should_get_bad_status_when_call_getAllTypes_without_user() throws Exception {
        //given
        String trd_session = "test";
        String open_id = "user id";
        when(loginService.getOpenId(trd_session)).thenReturn(open_id);
        when(userService.findUserByUserId(open_id)).thenReturn(null);
        //when
        //then
        mockMvc.perform(get("/api/v1/type/"+"?trd_session="+trd_session).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_addType_when_cll_addType() throws Exception {
        //given
        Type type=new Type();
        Map map = Maps.newHashMap("",type);
        String requestJson = JSONObject.toJSONString(map);
        doNothing().when(typeService).save(type);

        //when
        //then
        mockMvc.perform(post("/api/v1/type").content(JSONObject.toJSONString(map)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}