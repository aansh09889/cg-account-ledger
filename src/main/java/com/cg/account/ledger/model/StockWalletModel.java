package com.cg.account.ledger.model;

import java.util.Map;

import com.cg.account.ledger.constants.AssetType;

import lombok.Builder;
import lombok.Data;

@Data
public class StockWalletModel extends WalletModel {
    private Map<String,StockDataModel> stockData;

    @Builder
    public StockWalletModel(String walletId, String accountId, Map<String,StockDataModel> stockData) {
        super(walletId,accountId, AssetType.STOCK);
        this.stockData = stockData;
    }
}
