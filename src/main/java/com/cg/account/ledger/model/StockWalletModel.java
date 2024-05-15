package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

import com.cg.account.ledger.constants.AssetType;

@Data
public class StockWalletModel extends WalletModel {
    private Map<String,StockDataModel> stockData;

    @Builder
    public StockWalletModel(String walletId, String accountId, Map<String,StockDataModel> stockData) {
        super(walletId,accountId, AssetType.STOCK);
        this.stockData = stockData;
    }
}
