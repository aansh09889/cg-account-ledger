package com.cg.account.ledger.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.AssetType;
import com.cg.account.ledger.constants.StockSymbol;

@Entity
@Data
@Table(name = "stock_wallet")
@NoArgsConstructor
public class StockWallet extends Wallet{
    @Column(name = "stock_symbol")
    @Enumerated(EnumType.STRING)
    private StockSymbol stockSymbol;

    @Column(name = "balance_qty")
    private BigDecimal balanceQty;

    @Builder
    public StockWallet(String walletId, Account account, StockSymbol stockSymbol, BigDecimal balanceQty,LocalDateTime timestamp) {
        super(walletId,account, AssetType.STOCK,timestamp);
        this.stockSymbol=stockSymbol;
        this.balanceQty = balanceQty;
    }
}