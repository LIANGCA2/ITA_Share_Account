package com.oocl.ita.demo.repositories;

import com.oocl.ita.demo.entites.Account;
import com.oocl.ita.demo.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    List<Account> findAccountsByDateBetween(Date startDate, Date endDate);
    List<Account> findAllByUserAndDateBetween(User user, Date startDate, Date endDate);
    List<Account> findAllByIsDelete(String deleted);
}
