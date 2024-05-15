package com.cg.account.ledger.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.INRWallet;

public interface FiatUSDRepository extends JpaRepository<INRWallet, Long> {
//	BigDecimal getBalancefromId(long id);

}
