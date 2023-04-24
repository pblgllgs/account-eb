package com.pblgllgs.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pblgllgs.account.config.AccountsServiceConfig;
import com.pblgllgs.account.model.*;
import com.pblgllgs.account.repository.AccountRepository;
import com.pblgllgs.account.service.client.CardsFeignClient;
import com.pblgllgs.account.service.client.LoansFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AccountsServiceConfig accountsConfig;

    @Autowired
    LoansFeignClient loansFeignClient;

    @Autowired
    CardsFeignClient cardsFeignClient;

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer){
        Account account =  accountRepository.findByCustomerId(customer.getCustomerId());
        if(account != null){
            return account;
        }else{
            return null;
        }
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsConfig.getMsg(), accountsConfig.getBuildVersion(),
                accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @PostMapping("/myCustomerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp",fallbackMethod="myCustomerDetailsFallBack")
    @Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
    public CustomerDetails myCustomerDetails(
            @RequestHeader("pblgllgs-correlation-id") String correlationid,
            @RequestBody Customer customer) {
        Account accounts = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(correlationid,customer);
        List<Cards> cards = cardsFeignClient.getCardDetails(correlationid,customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);
        return customerDetails;
    }

    private CustomerDetails myCustomerDetailsFallBack(
            @RequestHeader("pblgllgs-correlation-id") String correlationid,
            Customer customer, Throwable t) {
        Account accounts = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(correlationid,customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(accounts);
        customerDetails.setLoans(loans);
        return customerDetails;
    }
}
