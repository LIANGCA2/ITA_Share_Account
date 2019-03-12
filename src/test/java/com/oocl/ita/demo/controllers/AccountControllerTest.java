package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.Type;
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

import java.text.*;
import java.util.*;

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

    @Test
    public void should_return_all_undeleted_accounts_of_that_month_when_call_getAccountsByMonth_given_year_and_month() {

    }

    private List<Account> mockAccountList() throws ParseException {
        Account account1 = mockAccount(1, "1", "收红包", mockAccountCreateDate("2019-01-01"), 12.0, "0", "Test1");
        Account account2 = mockAccount(2, "0", "餐饮", mockAccountCreateDate("2019-03-12"), 10.0, "0", "Test2");
        return new ArrayList<>(Arrays.asList(account1, account2));
    }

    private Date mockAccountCreateDate(String time) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        return ft.parse(time);
    }

    private Account mockAccount(Integer id, String accountType, String type, Date date, Double amount, String isDeleted, String remark) {
        Account account1 = new Account();
        account1.setId(id);
        Type tp = new Type();
        tp.setAccountKind(accountType);
        tp.setType(type);
        account1.setType(tp);
        account1.setDate(date);
        account1.setAmount(amount);
        account1.setIsDelete(isDeleted);
        account1.setRemark(remark);
        return account1;
    }
}