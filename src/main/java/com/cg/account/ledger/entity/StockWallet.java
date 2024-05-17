package com.cg.account.ledger.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.AssetType;
import com.cg.account.ledger.constants.StockSymbol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
