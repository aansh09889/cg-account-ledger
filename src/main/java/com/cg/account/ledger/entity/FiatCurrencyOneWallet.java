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
@Table(name="fiat_currency_one_wallet")
@NoArgsConstructor
public class FiatCurrencyOneWallet extends Wallet{
    @Column(name = "balance")
    private BigDecimal balance;

    @Builder
    public FiatCurrencyOneWallet(String walletId, Account account, BigDecimal balance,LocalDateTime timestamp) {
        super(walletId,account, AssetType.FIAT_CURRENCY_ONE,timestamp);
        this.balance=balance;
    }
}
