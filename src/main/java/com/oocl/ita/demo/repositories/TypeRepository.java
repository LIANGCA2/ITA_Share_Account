package com.oocl.ita.demo.repositories;

import com.oocl.ita.demo.entites.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
}
