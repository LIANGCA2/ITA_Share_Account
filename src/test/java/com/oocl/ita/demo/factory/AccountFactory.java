package com.oocl.ita.demo.factory;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.Type;

import java.text.*;
import java.util.*;

public class AccountFactory {
    public static Account mockAccount(Integer id, String accountType, String type, Date date, Double amount, String isDeleted, String remark) {
        Account account1 = new Account();
        account1.setId(id);
        Type tp = new Type();
        tp.setAccountKind(accountType);
        tp.setType(type);
        account1.setType(tp);
        account1.setDate(date);
        account1.setAmount(amount);
        account1.setIsDelete(isDeleted);
        account1.setRemark(remark);
        return account1;
    }

    public static Date mockAccountCreateDate(String time) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.parse(time);
    }
}
