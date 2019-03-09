package com.oocl.ita.demo.entites;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    public Account(){

    }

    private Integer id;
    private User user;
    private Type type;
    private Double account;
    private Boolean


}
