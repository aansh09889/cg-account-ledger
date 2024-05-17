package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.StockWallet;

public interface StockRepository extends JpaRepository<StockWallet, Long>{
}
