package com.cg.account.ledger.model;

import com.cg.account.ledger.constants.AssetType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WalletModel {
    private String walletId;
    private String accountId;
    private AssetType assetType;
}
