package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
