package com.cg.account.ledger.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.account.ledger.model.GetWalletBalanceQuery;
import com.cg.account.ledger.model.WalletBalance;

@RestController
@RequestMapping("/api/accounts")
public class WalletBalanceQueryController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/{walletId}/balance")
	public WalletBalance getWalletBalance(@PathVariable(value = "walletId") String walletId) {
		return queryGateway.query(GetWalletBalanceQuery.builder().walletid(walletId).build(),
				ResponseTypes.instanceOf(WalletBalance.class)).join();
	}
}
