package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, String>{
	Wallet findByWalletId(String walletId);
}
