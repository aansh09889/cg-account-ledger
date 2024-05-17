package com.cg.account.ledger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetWalletBalanceQuery {

	private String walletid;
}
