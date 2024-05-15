package com.cg.account.ledger.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.HKDWallet;

public interface FiatHKDRepository extends JpaRepository<HKDWallet, Long>{

	//BigDecimal getBalancefromId(long id);
}
