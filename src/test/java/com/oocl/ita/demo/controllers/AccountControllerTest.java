package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.factory.AccountFactory;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.services.AccountService;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;

import java.text.*;
import java.util.*;

import static com.oocl.ita.demo.factory.AccountFactory.mockAccount;
import static com.oocl.ita.demo.factory.AccountFactory.mockAccountCreateDate;
import static org.junit.Assert.assertEquals;

@RunWith(JMockit.class)
public class AccountControllerTest {
    @Tested(fullyInitialized = true) private AccountController accountController;

    @Injectable private AccountService accountService;

    @Injectable private LoginService loginService;

    @Injectable private UserService userService;

    @Test
    public void should_get_all_accounts_when_call_getAccounts_given_get_request() {
        new MockUp<AccountService>() {
            @Mock
            List<Account> getAllUndeletedAccounts() throws ParseException {
                return mockAccountList();
            }
        };
        List<Account> accounts = accountController.getAccounts();

        assertEquals(2, accounts.size());
        assertEquals("收红包", accounts.get(0).getType().getType());
    }

    private List<Account> mockAccountList() throws ParseException {
        Account account1 = mockAccount(1, "1", "收红包", mockAccountCreateDate("2019-01-01"), 12.0, "0", "Test1");
        Account account2 = mockAccount(2, "0", "餐饮", mockAccountCreateDate("2019-03-12"), 10.0, "0", "Test2");
        return new ArrayList<>(Arrays.asList(account1, account2));
    }

}