package com.cg.account.ledger.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.AssetType;
import com.cg.account.ledger.constants.CryptoType;

@Entity
@Table(name = "crypto_wallet")
@Data
@NoArgsConstructor
public class CryptoWallet extends Wallet{

    @Column(name = "crypto_type")
    @Enumerated(EnumType.STRING)
    private CryptoType cryptoType;

    @Column(name = "balance_qty")
    private BigDecimal balanceQty;

    @Builder
    public CryptoWallet(String walletId, Account account,CryptoType cryptoType,BigDecimal balanceQty,LocalDateTime timestamp) {
        super(walletId,account, AssetType.CRYPTO,timestamp);
        this.cryptoType = cryptoType;
        this.balanceQty = balanceQty;
    }
}
