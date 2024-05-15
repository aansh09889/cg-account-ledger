package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletBalance {

	private String walletid;
	private String assetType;
	private String balance;
}
