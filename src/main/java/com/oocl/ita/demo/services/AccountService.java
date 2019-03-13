package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Type;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.util.DateUtil;
import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.po.DayOfBill;
import com.oocl.ita.demo.po.MonthIO;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.po.Record;
import com.oocl.ita.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.text.*;
import java.util.*;
import java.util.stream.*;

@Service("accountService")
public class AccountService {
    private final AccountRepository accountRepository;
    private final TypeService typeService;

    @Autowired
    public AccountService(AccountRepository accountRepository, TypeService typeService) {
        this.accountRepository = accountRepository;
        this.typeService = typeService;
    }

    public void save(Account account, User user) {
        Type type = typeService.findTypeByTypeName(account.getType().getType());
        if(type == null) {
            typeService.save(account.getType());
        } else account.setType(type);
        account.setUser(user);
        account.setDate(DateUtil.getDateFromString(account.getDateStr()));
        account.setDateStr(null);
        account.setIsDelete("0");
        accountRepository.save(account);
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
        List<Account> allAccounts = accountRepository.findAll();
        return allAccounts.stream().filter(account -> account.getIsDelete().equals("0")).collect(Collectors.toList());
    }

    public boolean updateAccountById(Integer id, Account newAccount) {
        Account oldAccount = accountRepository.findById(id).orElse(null);
        if (null != oldAccount) {
            oldAccount.setIsDelete("0");
            oldAccount.setAmount(newAccount.getAmount());
            setDate(oldAccount, newAccount);
//            oldAccount.setDate(newAccount.getDate());
            oldAccount.setType(typeService.findTypeByTypeName(newAccount.getType().getType()));
            oldAccount.setRemark(newAccount.getRemark());
            accountRepository.save(oldAccount);
            return true;
        }
        return false;
    }

    private void setDate(Account oldAccount, Account newAccount) {
        try{
            if(newAccount.getDateStr() != null) {
                oldAccount.setDate(DateUtil.getDateFromString(newAccount.getDateStr()));
                oldAccount.setDateStr(null);
            } else oldAccount.setDate(newAccount.getDate());
        }catch (Exception e) {
            oldAccount.setDate(newAccount.getDate());
        }
    }

    public MonthOfBill getAccountsByMonth(String time, User user) {
        MonthOfBill monthOfBill = new MonthOfBill();
        monthOfBill.setDate(time);
        List<Account> accountList = getAccountsOfMonthByTime(time, user);
        Double totalIncome = accountList.stream().filter(account -> account.getType().getAccountKind().equals("income")).map(Account::getAmount)
            .reduce(new Double(0), (a, b) -> (a + b));
        Double totalOutlay = accountList.stream().filter(account -> account.getType().getAccountKind().equals("outlay")).map(Account::getAmount)
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
            dayOfBill.setIncome(accounts.stream().filter(account -> account.getType().getAccountKind().equals("income")).map(Account::getAmount)
                .reduce(new Double(0), (a, b) -> a + b));
            dayOfBill.setOutlay(accounts.stream().filter(account -> account.getType().getAccountKind().equals("outlay")).map(Account::getAmount)
                .reduce(new Double(0), (a, b) -> a + b));
            List<Record> records = new ArrayList<>();
            for (Account account : accounts) {
                Record record = new Record();
                record.setId(account.getId());
                record.setType(account.getType().getType());
                record.setMoney((account.getType().getAccountKind().equals("income") ? "+" : "-") + account.getAmount());
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


    private List<Account> getAccountsOfMonthByTime(String time, User user) {
        Date startDate = DateUtil.getFirstDateInMonth(time);
        Date endDate = DateUtil.getLastDateInMonth(time);
        return accountRepository.findAllByUserAndDateBetween(user, startDate, endDate).stream().filter(account -> account.getIsDelete().equals("0"))
            .collect(Collectors.toList());
    }

    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }
}
