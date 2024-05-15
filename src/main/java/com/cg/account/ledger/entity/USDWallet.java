package com.cg.account.ledger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.AssetType;

@Entity
@Data
@Table(name="fiat_usd_wallet")
@NoArgsConstructor
public class USDWallet extends Wallet {
    @Column(name = "balance")
    private BigDecimal balance;

    @Builder
    public USDWallet(String walletId, Account account, BigDecimal balance, LocalDateTime timestamp) {
        super(walletId, account, AssetType.FIAT_USD,timestamp);
        this.balance = balance;
    }
}