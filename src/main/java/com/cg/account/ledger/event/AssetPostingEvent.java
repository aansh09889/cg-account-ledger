package com.cg.account.ledger.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cg.account.ledger.constants.PostingStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetPostingEvent {
    private String postingId;
    private String accountId;
    private String fromWalletId;
    private String toWalletId;
    private BigDecimal txnAmount;
    private PostingStatus postingStatus;
    private LocalDateTime postingTime;

}
