package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.repositories.AccountRepository;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.*;
import java.util.*;

import static com.oocl.ita.demo.factory.AccountFactory.mockAccount;
import static com.oocl.ita.demo.factory.AccountFactory.mockAccountCreateDate;
import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class AccountServiceTest {
    @Tested private AccountService accountService;

    @Injectable private AccountRepository accountRepository;

    @Injectable private TypeService typeService;


    @Test
    public void should_return_all_undeleted_accounts_when_call_getAllUndeletedAccounts() throws ParseException {
        new Expectations(){{
            accountRepository.findAll();
            result = mockAccountList();
        }};

        List<Account> allUndeletedAccounts = accountService.getAllUndeletedAccounts();

        assertEquals(1, allUndeletedAccounts.size());
        assertEquals("收红包", allUndeletedAccounts.get(0).getType().getType());
    }

    private List<Account> mockAccountList() throws ParseException {
        Account account1 = mockAccount(1, "1", "收红包", mockAccountCreateDate("2019-01-01"), 12.0, "0", "Test1");
        Account account2 = mockAccount(2, "0", "餐饮", mockAccountCreateDate("2019-03-12"), 10.0, "1", "Test2");
        return new ArrayList<>(Arrays.asList(account1, account2));
    }
}