package com.pblgllgs.account.repository;

import com.pblgllgs.account.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findByCustomerId(int customerId);
}
