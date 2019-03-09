package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired AccountRepository accountRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public boolean deleteAccount(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (null != account && !Boolean.parseBoolean(account.getIsDelete())){
            accountRepository.delete(account);
            return true;
        }
        return false;
    }
}
