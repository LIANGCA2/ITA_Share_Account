package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.DayOfBill;
import com.oocl.ita.demo.po.MonthIO;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.po.Record;
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
import java.util.stream.*;

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
                return mockAccountList().stream().filter(account -> account.getIsDelete().equals("0")).collect(Collectors.toList());
            }
        };
        List<Account> accounts = accountController.getAccounts();

        assertEquals(4, accounts.size());
        assertEquals("收红包", accounts.get(0).getType().getType());
    }

    @Test
    public void should_return_all_undeleted_accounts_of_that_month_by_day_when_call_getAccountsByMonth_given_year_and_month() {
        mockOpenId();
        mockUser(new User());
        new MockUp<AccountService>(){
            @Mock
            public MonthOfBill getAccountsByMonth(String time, User user){
                MonthOfBill monthOfBill = new MonthOfBill();
                MonthIO monthIO = new MonthIO();
                monthIO.setIncome(100.00);
                monthIO.setOutlay(50.00);
                monthOfBill.setMonthIO(monthIO);
                DayOfBill dayOfBill = new DayOfBill();
                Record record = new Record();
                record.setType("餐饮");
                dayOfBill.getRecords().add(record);
                monthOfBill.getBill().add(dayOfBill);
                return monthOfBill;
            }
        };

        ResponseEntity<MonthOfBill> accountsByMonth = accountController.getAccountsByMonth("2019-03", "");

        assertEquals("餐饮",accountsByMonth.getBody().getBill().get(0).getRecords().get(0).getType());
        assertEquals(new Double(100.00),accountsByMonth.getBody().getMonthIO().getIncome());
        assertEquals(new Double(50.00),accountsByMonth.getBody().getMonthIO().getOutlay());
    }

    @Test
    public void should_return_bad_request_when_getAccountsByMonth_given_user_id_can_not_find_user() {
        mockOpenId();
        mockUser(null);

        ResponseEntity<MonthOfBill> monthOfBillResponseEntity = accountController.getAccountsByMonth("2019-03", "");

        assertEquals("Bad Request", monthOfBillResponseEntity.getStatusCode().getReasonPhrase());
    }

    private void mockUser(User user) {
        new MockUp<UserService>(){
            @Mock
            public User findUserByUserId(String userId){
                return user;
            }
        };
    }

    private void mockOpenId() {
        new MockUp<LoginService>(){
            @Mock
            String getOpenId(String loginStatus){
                return "openId";
            }
        };
    }

    private List<Account> mockAccountList() throws ParseException {
        Account account1 = mockAccount(1, "income", "收红包", mockAccountCreateDate("2019-01-01"), 12.0, "0", "Test1");
        Account account2 = mockAccount(2, "outlay", "餐饮", mockAccountCreateDate("2019-03-12"), 10.0, "0", "Test2");
        Account account3 = mockAccount(3, "outlay", "购物", mockAccountCreateDate("2019-03-10"), 12.0, "0", "Test2");
        Account account4 = mockAccount(4, "income", "工资", mockAccountCreateDate("2019-03-10"), 5000.0, "0", "Test2");
        Account account5 = mockAccount(5, "income", "工资", mockAccountCreateDate("2019-03-10"), 1.0, "1", "Test2");
        return new ArrayList<>(Arrays.asList(account1, account2, account3, account4, account5));
    }

}