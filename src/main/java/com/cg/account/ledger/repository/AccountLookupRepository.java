package com.cg.account.ledger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.AccountLookup;

public interface AccountLookupRepository extends JpaRepository<AccountLookup,String> {
    List<AccountLookup> findByAccountId(String accountId);
    AccountLookup findByAccountIdAndWalletId(String accountId,String walletId);
}
