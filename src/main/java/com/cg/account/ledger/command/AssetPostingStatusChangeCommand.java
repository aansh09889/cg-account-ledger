package com.cg.account.ledger.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cg.account.ledger.constants.PostingStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetPostingStatusChangeCommand {

	@TargetAggregateIdentifier
    private String postingId;
	
    private String accountId;
    
    private PostingStatus postingStatus;
}
