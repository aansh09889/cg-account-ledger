package com.cg.account.ledger.model;

import java.util.Map;

import com.cg.account.ledger.constants.AssetType;

import lombok.Builder;
import lombok.Data;

@Data
public class CryptoWalletModel extends WalletModel {
    private Map<String,CryptoDataModel> cryptoData;

    @Builder
    public CryptoWalletModel(String walletId, String accountId, Map<String,CryptoDataModel> cryptoData) {
        super(walletId,accountId, AssetType.CRYPTO);
        this.cryptoData = cryptoData;
    }
}