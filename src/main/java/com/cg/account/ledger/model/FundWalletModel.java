package com.cg.account.ledger.model;

import java.util.Map;

import com.cg.account.ledger.constants.AssetType;

import lombok.Builder;
import lombok.Data;

@Data
public class FundWalletModel extends WalletModel {
    private Map<String, FundDataModel> fundData;

    @Builder
    public FundWalletModel(String walletId, String accountId, Map<String, FundDataModel> fundData) {
        super(walletId,accountId, AssetType.FUND);
        this.fundData = fundData;
    }
}
