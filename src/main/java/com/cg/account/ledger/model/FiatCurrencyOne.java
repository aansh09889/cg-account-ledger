package com.cg.account.ledger.model;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.AssetType;

import lombok.Builder;
import lombok.Data;

@Data
public class FiatCurrencyOne extends WalletModel {
    private BigDecimal balance;

    @Builder
    public FiatCurrencyOne(String walletId, String accountId, BigDecimal balance) {
        super(walletId,accountId, AssetType.FIAT_CURRENCY_ONE);
        this.balance=balance;
    }
}
