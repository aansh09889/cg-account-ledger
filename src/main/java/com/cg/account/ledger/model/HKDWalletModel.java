package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.AssetType;

@Data
public class HKDWalletModel extends WalletModel {
    private BigDecimal balance;

    @Builder
    public HKDWalletModel(String walletId, String accountId, BigDecimal balance) {
        super(walletId,accountId, AssetType.FIAT_HKD);
        this.balance=balance;
    }
}
