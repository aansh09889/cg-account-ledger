package com.cg.account.ledger.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cg.account.ledger.constants.PostingStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetPostingStatusChangeEvent {

    private String postingId;
	
    private String accountId;
    
    private PostingStatus postingStatus;
}
