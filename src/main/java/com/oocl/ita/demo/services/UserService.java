package com.oocl.ita.demo.services;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.po.UserInfo;
import com.oocl.ita.demo.repositories.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Account> userAccounts = accounts.stream().filter( account -> account.getUser().getUserId().equals(userId)).collect(Collectors.toList());
        long timeNow = System.currentTimeMillis();
        userInfo.setDays(String.format("%d", (timeNow - user.getDate().getTime()) / oneDayTime));
        userInfo.setRecords(String.format("%d", userAccounts.size()));
        Double balance = 0.0;
        for(Account account : userAccounts) {
            if(account.isIncome()){
                balance += account.getAmount();
            } else {
                balance -= account.getAmount();
            }
        }
        userInfo.setBalance(String.format("%.2f", balance));
        return userInfo;
    }

    public void updateUserInfo(String userId, User user) {
        List<User> users = userRepository.findByUserId(userId);
        if(users == null || users.isEmpty()) return;
        User userInDb = users.get(0);
        String gender = user.getGender();
        if(StringUtils.isNotEmpty(gender)) {
            gender = gender.equals("1") ? "male" : "female";
        }
        userInDb.setGender(gender);
        userInDb.setImage(user.getImage());
        userInDb.setNickName(user.getNickName());
        userRepository.save(userInDb);
    }

    public User findUserByUserId(String userId) {
        if(userId == null) return null;
        List<User> users = userRepository.findByUserId(userId);
        if(users == null || users.isEmpty()) return null;
        return users.get(0);
    }
}
