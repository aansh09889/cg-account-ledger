package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.FiatCurrencyOneWallet;

public interface FiatHKDRepository extends JpaRepository<FiatCurrencyOneWallet, Long>{

}
