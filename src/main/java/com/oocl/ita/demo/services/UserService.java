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

    public UserInfo getUserInfo(String userId) {
        UserInfo userInfo = new UserInfo();
        List<User> users = userRepository.findByUserId(userId);
        List<Account> accounts = accountService.getAllUndeletedAccounts();
        if(users == null || users.isEmpty() || accounts.isEmpty()) return userInfo;
        User user = users.get(0);
        long timeNow = System.currentTimeMillis();
        userInfo.setDays(String.format("%d", (timeNow - user.getDate().getTime()) / oneDayTime));
        userInfo.setRecords(String.format("%d", accounts.size()));
        Double balance = 0.0;
        for(Account account : accounts) {
            if(account.isIncome()){
                balance += account.getAmount();
            } else {
                balance -= account.getAmount();
            }
        }
        userInfo.setBalance(String.format("%.2f", balance));
        return userInfo;
    }
}
