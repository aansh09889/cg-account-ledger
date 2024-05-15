package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.AssetType;

@Data
public class USDWalletModel extends WalletModel {
    private BigDecimal balance;

    @Builder
    public USDWalletModel(String walletId, String accountId, BigDecimal balance) {
        super(walletId, accountId, AssetType.FIAT_USD);
        this.balance = balance;
    }
}
