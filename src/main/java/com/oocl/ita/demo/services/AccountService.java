package com.oocl.ita.demo.services;

import com.oocl.ita.demo.Utils.DateUtil;
import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Account> getIncomeAccountsByDay(Date date) {
        return accountRepository.findAccountsByDate(date).stream()
            .filter(account -> account.getIsDelete().equals("0") && account.getAccountKind().equals("1")).collect(Collectors.toList());
    }

    public List<Account> getExpendAccountsByDay(Date date) {
        return accountRepository.findAccountsByDate(date).stream()
            .filter(account -> account.getIsDelete().equals("0") && account.getAccountKind().equals("0")).collect(Collectors.toList());
    }

    public List<Account> getIncomeAccountsByMonth(String time) {
        Date startDate = DateUtil.getFirstDateInMonth(time);
        Date endDate = DateUtil.getLastDateInMonth(time);
        List<Account> incomesOfMonth = accountRepository.findAccountsByDateBetweenOrderByDate(startDate, endDate).stream()
            .filter(account -> account.getIsDelete().equals("0") && account.getAccountKind().equals("1")).collect(Collectors.toList());
        return incomesOfMonth;
    }
}
