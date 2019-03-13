package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.repositories.AccountRepository;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.*;
import java.util.*;
import java.util.stream.Collectors;

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
            accountRepository.findAllByIsDelete("0");
            result = mockAccountList().stream().filter(account -> account.getIsDelete().equals("0")).collect(Collectors.toList());
        }};

        List<Account> allUndeletedAccounts = accountService.getAllUndeletedAccounts();

        assertEquals(4, allUndeletedAccounts.size());
        assertEquals("收红包", allUndeletedAccounts.get(0).getType().getType());
    }

    @Test
    public void should_return_month_of_accounts_when_getAccountsByMonth_given_user_and_time() throws ParseException {
        new Expectations(){{
            accountRepository.findAllByUserAndDateBetween((User)any, (Date)any, (Date)any);
            result = mockAccountList();
        }};

        MonthOfBill accountsByMonth = accountService.getAccountsByMonth("2019-03", new User());

        assertEquals(new Double(2012), accountsByMonth.getMonthIO().getIncome());
        assertEquals(new Double(70), accountsByMonth.getMonthIO().getOutlay());
        assertEquals(2, accountsByMonth.getBill().size());
        assertEquals("03月09日", accountsByMonth.getBill().get(0).getDate());
        assertEquals("03月11日", accountsByMonth.getBill().get(1).getDate());
        assertEquals(new Double(12), accountsByMonth.getBill().get(0).getIncome());
        assertEquals(new Double(2000), accountsByMonth.getBill().get(1).getIncome());
    }

    public static List<Account> mockAccountList() throws ParseException {
        Account account1 = mockAccount(1, "income", "收红包", mockAccountCreateDate("2019-03-09"), 12.0, "0", "Test1");
        Account account2 = mockAccount(2, "outlay", "餐饮", mockAccountCreateDate("2019-03-12"), 10.0, "1", "Test2");
        Account account3 = mockAccount(3, "outlay", "购物", mockAccountCreateDate("2019-03-11"), 50.0, "0", "Test3");
        Account account4 = mockAccount(4, "outlay", "餐饮", mockAccountCreateDate("2019-03-11"), 20.0, "0", "Test4");
        Account account5 = mockAccount(5, "income", "工资", mockAccountCreateDate("2019-03-11"), 2000.0, "0", "Test5");
        return new ArrayList<>(Arrays.asList(account1, account2,account3,account4,account5));
    }
}