package com.oocl.ita.demo.controllers;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.exceptions.BadRequestException;
import com.oocl.ita.demo.services.AccountService;
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

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional
    @PostMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account addAccount(@RequestBody Account account) {
        return accountService.save(account);
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
        return accountService.getAllUndeletedAccounts();
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateAccountById(@PathVariable Integer id, @RequestBody Account newAccount){
        if(accountService.updateAccountById(id, newAccount)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new BadRequestException("update error");
    }

    @GetMapping(path = "/income/{date}")
    public List<Account> getIncomeAccountsByDay(@PathVariable Date date){
        return accountService.getIncomeAccountsByDay(date);

    }

    @GetMapping(path = "/expend/{date}")
    public List<Account> getExpendAccountsByDay(@PathVariable Date date){
        return accountService.getExpendAccountsByDay(date);
    }

    @GetMapping(path = "/{time}")
    public List<Account> getAccountsByMonth(@PathVariable String time){
        return accountService.getIncomeAccountsByMonth(time);

    }
}
