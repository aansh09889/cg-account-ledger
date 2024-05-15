package com.cg.account.ledger.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.FundWallet;

public interface FundRepository extends JpaRepository<FundWallet, Long>{
//	BigDecimal getBalancefromId(long id);

}
