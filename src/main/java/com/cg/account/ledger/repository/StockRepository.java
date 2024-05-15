package com.cg.account.ledger.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.StockWallet;

public interface StockRepository extends JpaRepository<StockWallet, Long>{
	//BigDecimal getBalanceQtyfromId(long id);

}
