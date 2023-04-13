package com.pblgllgs.account.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_number")
    private long accountNumber;

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "account_type",nullable = false)
    private String accountType;

    @Column(name = "branch_address",nullable = false)
    private String branchAddress;

    @Column(name = "create_dt")
    private LocalDate createDt;
}
