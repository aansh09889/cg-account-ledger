package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.USDWallet;

public interface FiatUSDRepository extends JpaRepository<USDWallet, Long> {
//	BigDecimal getBalancefromId(long id);

}
