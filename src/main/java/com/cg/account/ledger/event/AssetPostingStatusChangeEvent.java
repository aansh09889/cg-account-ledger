package com.cg.account.ledger.event;

import com.cg.account.ledger.constants.PostingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AssetPostingStatusChangeEvent {

    private String postingId;
	
    private String accountId;
    
    private PostingStatus postingStatus;
}
