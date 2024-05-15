package com.cg.account.ledger.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.PostingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "posting")
public class Posting {

    @Id
    @Column(name = "posting_id")
    private String postingId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "posting_status")
    @Enumerated(EnumType.STRING)
    private PostingStatus postingStatus;

    @Column(name = "from_wallet_id")
    private String fromWalletId;

    @Column(name = "to_wallet_id")
    private String toWalletId;

    @Column(name = "txn_amount")
    private BigDecimal txnAmount;

    @Column(name = "posting_time")
    private LocalDateTime postingTime;

}
