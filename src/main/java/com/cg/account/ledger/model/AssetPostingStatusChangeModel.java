package com.cg.account.ledger.model;

import com.cg.account.ledger.constants.PostingStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetPostingStatusChangeModel {

	@NotBlank(message = "postingId is a required field.")
    private String postingId;
	@NotBlank(message = "postingId is a required field.")
    private String accountId;
    private PostingStatus postingStatus;
}
