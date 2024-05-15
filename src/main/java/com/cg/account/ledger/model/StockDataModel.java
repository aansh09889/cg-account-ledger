package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.StockSymbol;

@Data
@Builder
public class StockDataModel {
    private StockSymbol stockSymbol;
    private BigDecimal balanceQty;
}
