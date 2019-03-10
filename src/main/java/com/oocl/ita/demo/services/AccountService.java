package com.oocl.ita.demo.services;

import com.oocl.ita.demo.util.DateUtil;
import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.po.DayOfBill;
import com.oocl.ita.demo.po.MonthIO;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.po.Record;
import com.oocl.ita.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.*;
import java.util.*;
import java.util.stream.*;

@Service("accountService")
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        account.setDate(new Date());
        return accountRepository.save(account);
    }

    public boolean deleteAccount(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (null != account && !Boolean.parseBoolean(account.getIsDelete())) {
            account.setIsDelete(String.valueOf(1));
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public List<Account> getAllUndeletedAccounts() {
        return accountRepository.findAll().stream().filter(account -> account.getIsDelete().equals("0")).collect(Collectors.toList());
    }

    public boolean updateAccountById(Integer id, Account newAccount) {
        Account oldAccount = accountRepository.findById(id).orElse(null);
        if (null != oldAccount) {
            oldAccount.setIsDelete("0");
            oldAccount.setAccountKind(newAccount.getAccountKind());
            oldAccount.setAmount(newAccount.getAmount());
            oldAccount.setDate(new Date());
            oldAccount.setType(newAccount.getType());
            accountRepository.save(oldAccount);
            return true;
        }
        return false;
    }

    public MonthOfBill getAccountsByMonth(String time) {
        MonthOfBill monthOfBill = new MonthOfBill();
        monthOfBill.setDate(time);
        List<Account> accountList = getAccountsOfMonthByTime(time);
        Double totalIncome = accountList.stream().filter(account -> account.getAccountKind().equals("1")).map(Account::getAmount)
            .reduce(new Double(0), (a, b) -> (a + b));
        Double totalOutlay = accountList.stream().filter(account -> account.getAccountKind().equals("0")).map(Account::getAmount)
            .reduce(new Double(0), (a, b) -> (a + b));
        MonthIO monthIO = new MonthIO();
        monthIO.setIncome(totalIncome);
        monthIO.setOutlay(totalOutlay);
        monthIO.setBalance(totalIncome - totalOutlay);
        monthOfBill.setMonthIO(monthIO);
        Map<String, List<Account>> billMap = getBillMap(accountList);
        for (String key : billMap.keySet()) {
            List<Account> accounts = billMap.get(key);
            DayOfBill dayOfBill = new DayOfBill();
            dayOfBill.setDate(key);
            dayOfBill.setIncome(accounts.stream().filter(account -> account.getAccountKind().equals("1")).map(Account::getAmount)
                .reduce(new Double(0), (a, b) -> a + b));
            dayOfBill.setOutlay(accounts.stream().filter(account -> account.getAccountKind().equals("0")).map(Account::getAmount)
                .reduce(new Double(0), (a, b) -> a + b));
            List<Record> records = new ArrayList<>();
            for (Account account : accounts) {
                Record record = new Record();
                record.setId(account.getId());
                record.setType(account.getType().getType());
                record.setMoney((account.getAccountKind().equals("1") ? "+" : "-") + account.getAmount());
                records.add(record);
            }
            dayOfBill.setRecords(records);
            monthOfBill.getBill().add(dayOfBill);
        }
        return monthOfBill;
    }

    private Map<String, List<Account>> getBillMap(List<Account> accountList) {
        Map<String, List<Account>> billMap = new TreeMap<>();
        for (Account account : accountList) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
            String key = simpleDateFormat.format(account.getDate());

            if (billMap.containsKey(key)) {
                billMap.get(key).add(account);
            } else {
                List<Account> accounts = new ArrayList<>();
                accounts.add(account);
                billMap.put(key, accounts);
            }
        }
        return billMap;
    }


    private List<Account> getAccountsOfMonthByTime(String time) {
        Date startDate = DateUtil.getFirstDateInMonth(time);
        Date endDate = DateUtil.getLastDateInMonth(time);
        return accountRepository.findAccountsByDateBetween(startDate, endDate).stream().filter(account -> account.getIsDelete().equals("0"))
            .collect(Collectors.toList());
    }

    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }
}
