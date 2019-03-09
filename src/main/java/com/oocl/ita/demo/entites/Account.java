package com.oocl.ita.demo.entites;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    public Account(){

    }

    private Integer id;
    private User user;
    private Type type;
    private Double account;
    private Boolean accountKind;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "type_id")
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    @Column(name = "account_kind")
    public Boolean getAccountKind() {
        return accountKind;
    }

    public void setAccountKind(Boolean accountKind) {
        this.accountKind = accountKind;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
