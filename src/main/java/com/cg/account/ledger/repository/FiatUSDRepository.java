package com.cg.account.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.account.ledger.entity.FiatCurrencyTwoWallet;

public interface FiatUSDRepository extends JpaRepository<FiatCurrencyTwoWallet, Long> {

}
