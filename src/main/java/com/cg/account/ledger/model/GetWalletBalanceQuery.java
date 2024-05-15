package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWalletBalanceQuery {

	private String walletid;
	private String timeStamp;
}
