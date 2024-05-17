package com.cg.account.ledger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WalletBalance {

	private String walletid;
	private String assetType;
	private String balance;
}
