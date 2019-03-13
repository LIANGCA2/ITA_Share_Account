package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountService accountService;

    @Test
    public void should_return_null_when_call_findUserByUserId_given_userId_null() {
        User result = userService.findUserByUserId(null);
        assertNull(result);
    }

    @Test
    public void should_return_null_when_call_findUserByUserId_given_userId_cant_find_in_db() {
        String userId = "userId";
        when(userRepository.findByUserId(userId)).thenReturn(null);
        User result = userService.findUserByUserId(userId);
        assertNull(result);
    }

    @Test
    public void should_return_first_user_when_call_findUserByUserId_given_userId_valid() {
        String userId = "userId";
        List<User> users = new ArrayList<>();
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        users.add(user);
        when(userRepository.findByUserId(userId)).thenReturn(users);
        User result = userService.findUserByUserId(userId);
        assertEquals( (Integer) 1, result.getId());
    }

    @Test
    public void should_not_save_when_call_updateUserInfo_given_userId_not_in_db() {
        String userId = "userID";
        User user = mock(User.class);
        when(userRepository.findByUserId(userId)).thenReturn(new ArrayList<>());
        userService.updateUserInfo(userId, user);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void should_save_when_call_updateUserInfo_given_userId_in_db() {
        String userId = "userID";
        List<User> users = new ArrayList<>();
        User user = mock(User.class);
        users.add(user);
        when(user.getGender()).thenReturn("female");
        when(user.getImage()).thenReturn("image path");
        when(user.getNickName()).thenReturn("nick name");
        when(userRepository.findByUserId(userId)).thenReturn(users);

        userService.updateUserInfo(userId, user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void should_return_empty_userInfo_when_call_getUserInfo_given_userId_not_in_db(){
        String userId = "userId";
        List<User> users = new ArrayList<>();
        User user = mock(User.class);
        users.add(user);
        when(userRepository.findByUserId(userId)).thenReturn(users);
        when(accountService.getAllUndeletedAccounts()).thenReturn(new ArrayList<>());

        UserInfo result = userService.getUserInfo(userId);
        assertNull(result.getBalance());
        assertNull(result.getRecords());
        assertNull(result.getDays());
    }

    @Test
    public void should_return_userInfo_when_call_getUserInfo_given_userId_valid() {
        String userId = "userId";
        List<User> users = new ArrayList<>();
        User user = mock(User.class);
        User user1 = mock(User.class);
        users.add(user);
        when(user.getUserId()).thenReturn(userId);
        List<Account> accounts = new ArrayList<>();
        Account account1 = mock(Account.class);
        Account account2 = mock(Account.class);
        Account account3 = mock(Account.class);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        when(account1.getUser()).thenReturn(user);
        when(account3.getUser()).thenReturn(user);
        when(account2.getUser()).thenReturn(user1);

        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10000000L);
        when(user.getDate()).thenReturn(date);
        when(account1.isIncome()).thenReturn(true);
        when(account1.getAmount()).thenReturn(2.0);
        when(account3.isIncome()).thenReturn(false);
        when(account3.getAmount()).thenReturn(1.0);
        when(accountService.getAllUndeletedAccounts()).thenReturn(accounts);
        when(userRepository.findByUserId(userId)).thenReturn(users);

        UserInfo result = userService.getUserInfo(userId);

        assertEquals("1.00", result.getBalance());
        assertEquals("2", result.getRecords());
    }

}