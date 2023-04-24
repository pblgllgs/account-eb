package com.pblgllgs.account.service.client;

import com.pblgllgs.account.model.Customer;
import com.pblgllgs.account.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "myLoans" , consumes = "application/json")
    List<Loans> getLoansDetails(@RequestHeader("pblgllgs-correlation-id") String correlationid, @RequestBody Customer customer);
}
