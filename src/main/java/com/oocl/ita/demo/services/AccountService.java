package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("accountService")
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public boolean deleteAccount(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (null != account && !Boolean.parseBoolean(account.getIsDelete())){
            account.setIsDelete(String.valueOf(1));
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
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
}
