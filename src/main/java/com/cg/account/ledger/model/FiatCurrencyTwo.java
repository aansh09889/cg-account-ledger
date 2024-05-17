package com.cg.account.ledger.model;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.AssetType;

import lombok.Builder;
import lombok.Data;

@Data
public class FiatCurrencyTwo extends WalletModel {
    private BigDecimal balance;

    @Builder
    public FiatCurrencyTwo(String walletId, String accountId, BigDecimal balance) {
        super(walletId, accountId, AssetType.FIAT_CURRENCY_TWO);
        this.balance = balance;
    }
}
