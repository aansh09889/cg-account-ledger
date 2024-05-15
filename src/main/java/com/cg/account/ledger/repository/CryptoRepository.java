package com.cg.account.ledger.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.CryptoWallet;

public interface CryptoRepository extends JpaRepository<CryptoWallet, Long>{
	//BigDecimal getBalanceQtyfromId(long id);

}
