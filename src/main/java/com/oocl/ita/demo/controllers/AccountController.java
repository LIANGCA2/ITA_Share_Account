package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import com.oocl.ita.demo.exceptions.BadRequestException;
import com.oocl.ita.demo.po.MonthOfBill;
import com.oocl.ita.demo.services.AccountService;
import com.oocl.ita.demo.services.LoginService;
import com.oocl.ita.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public AccountController(AccountService accountService, LoginService loginService, UserService userService) {
        this.accountService = accountService;
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addAccount(@RequestBody Account account, @RequestParam String trd_session) {
        String openId = loginService.getOpenId(trd_session);
        User user = userService.findUserByUserId(openId);
        if(user == null) return ResponseEntity.badRequest().build();
        accountService.save(account, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAccountByID(@PathVariable Integer id){
        if (accountService.deleteAccount(id)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new BadRequestException("Delete error");
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAccounts(){
        List<Account> accounts = accountService.getAllUndeletedAccounts();
        for(Account account : accounts) {
            account.getUser().setUserId(null);
        }
        return accounts;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccountById(@PathVariable Integer id){
        return accountService.getAccountById(id);
    }

    @PostMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateAccountById(@PathVariable Integer id, @RequestBody Account newAccount, @RequestParam String trd_session){
        String openId = loginService.getOpenId(trd_session);
        User user = userService.findUserByUserId(openId);
        if(user == null) return ResponseEntity.badRequest().build();
        if(accountService.updateAccountById(id, newAccount)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new BadRequestException("update error");
    }

    @GetMapping(path = "/month/{time}")
    public ResponseEntity<MonthOfBill> getAccountsByMonth(@PathVariable String time, @RequestParam String trd_session){
        HttpStatus httpStatus = HttpStatus.OK;
        String openId = loginService.getOpenId(trd_session);
        User user = userService.findUserByUserId(openId);
        if(user == null){
            return ResponseEntity.badRequest().body(new MonthOfBill());
        }
        MonthOfBill monthOfBill = accountService.getAccountsByMonth(time, user);
        return new ResponseEntity<>(monthOfBill, httpStatus);
    }
}
