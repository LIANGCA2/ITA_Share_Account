package com.oocl.ita.demo.repositories;

import com.oocl.ita.demo.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
}
