package com.oocl.ita.demo.controllers; 

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/** 
* UserController Tester. 
* 
* @author <Authors name> 
* @since <pre>Mar 12, 2019</pre> 
* @version 1.0 
*/ 
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@EnableSpringDataWebSupport
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
    public void should_get_UserInfo_when_call_getUserInfo_given_trd_session(){
    // given
        UserInfo userInfo = new UserInfo();
    // when
        ;   
    // then
        ;   
    }

} 
