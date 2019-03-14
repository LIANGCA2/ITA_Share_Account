package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.factory.AccountFactory;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.repositories.AccountRepository;
import com.oocl.ita.demo.repositories.TypeRepository;
import com.oocl.ita.demo.repositories.UserRepository;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTestV2 {

    @MockBean
    private  TypeService typeService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TypeRepository typeRepository;

    @Autowired
    private AccountService accountService;


    @Test
    public void should_save_when_call_account_save_given_type_in_db() {
        User user = mock(User.class);
        Type type =mock(Type.class);
        List<Type> list = new ArrayList<>();
        list.add(type);
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");
        when(typeService.findTypeByTypeName(account.getType().getType())).thenReturn(type);
        accountService.save(account,user);
        verify(accountRepository, times(1)).save(any());
    }



    @Test
    public void should_save_when_call_account_save_given_type_not_in_db() {
        User user = mock(User.class);
        Type type =mock(Type.class);
        List<Type> list = new ArrayList<>();
        list.add(type);
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");
        when(typeService.findTypeByTypeName(account.getType().getType())).thenReturn(null);
        doNothing().when(typeService).save(account.getType());
        accountService.save(account,user);
        verify(accountRepository, times(1)).save(any());
    }


    @Test
    public void should_delete_account_when_call_deleteAccount_given_id_in_db() {
        Integer id = 1;
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        accountService.deleteAccount(id);
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    public void should_delete_account_when_call_deleteAccount_given_id_not_in_db() {
        Integer id = 1;
        Account account =AccountFactory.mockAccount(1,"1","1",new Date(),2.00,"0","");
        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        accountService.deleteAccount(id);
        verify(accountRepository, times(0)).save(any());
    }

    
}