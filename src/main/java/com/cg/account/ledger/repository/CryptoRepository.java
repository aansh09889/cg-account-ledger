package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.CryptoWallet;

public interface CryptoRepository extends JpaRepository<CryptoWallet, Long>{

}
