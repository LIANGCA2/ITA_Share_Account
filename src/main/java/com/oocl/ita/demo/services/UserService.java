package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;

    private final long oneDayTime = 1000 * 60 * 60 * 24;

    @Autowired
    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public UserInfo getUserInfo(int id) {
        UserInfo userInfo = new UserInfo();
        User user = userRepository.findById(id).orElse(null);
        List<Account> accounts = accountService.getAllUndeletedAccounts();
        if(user == null || accounts.isEmpty()) return userInfo;
        long timeNow = System.currentTimeMillis();
        userInfo.setDay((timeNow - user.getDate().getTime()) / oneDayTime);
        userInfo.setCount(accounts.size());
        Double balance = 0.0;
        for(Account account : accounts) {
            if(account.isIncome()){
                balance += account.getAmount();
            } else {
                balance -= account.getAmount();
            }
        }
        userInfo.setBalance(balance);
        return userInfo;
    }
}
