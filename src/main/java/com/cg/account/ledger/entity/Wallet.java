package com.cg.account.ledger.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.cg.account.ledger.constants.AssetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="wallet_id")
    private String walletId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "asset_type")
    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(name = "time_stamp")
    private LocalDateTime timestamp;

    public Wallet() {}

    public Wallet(String walletId, Account account, AssetType assetType, LocalDateTime timestamp) {
        this.walletId = walletId;
        this.account = account;
        this.assetType = assetType;
        this.timestamp = timestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return walletId.equals(wallet.walletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId);
    }
}
