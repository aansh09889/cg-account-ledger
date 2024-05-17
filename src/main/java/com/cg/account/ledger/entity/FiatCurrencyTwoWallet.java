package com.cg.account.ledger.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.AssetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="fiat_currency_two_wallet")
@NoArgsConstructor
public class FiatCurrencyTwoWallet extends Wallet {
    @Column(name = "balance")
    private BigDecimal balance;

    @Builder
    public FiatCurrencyTwoWallet(String walletId, Account account, BigDecimal balance, LocalDateTime timestamp) {
        super(walletId, account, AssetType.FIAT_CURRENCY_TWO,timestamp);
        this.balance = balance;
    }
}