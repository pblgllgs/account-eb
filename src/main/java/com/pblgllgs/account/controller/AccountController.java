package com.pblgllgs.account.controller;

import com.pblgllgs.account.model.Account;
import com.pblgllgs.account.model.Customer;
import com.pblgllgs.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer){
        Account account =  accountRepository.findByCustomerId(customer.getCustomerId());
        if(account != null){
            return account;
        }else{
            return null;
        }
    }
}
