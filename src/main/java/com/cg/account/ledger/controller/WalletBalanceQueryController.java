package com.cg.account.ledger.controller;

import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@GetMapping("/{walletId}/{timeStamp}/balance")
	public WalletBalance getWalletBalance(@PathVariable(value = "walletId") String walletId,
			@PathVariable(value = "timeStamp") String timeStamp) {

		//GetWalletBalanceQuery balanceQuery = new GetWalletBalanceQuery();
		return queryGateway.query(GetWalletBalanceQuery.builder().walletid(walletId).timeStamp(timeStamp).build(), ResponseTypes.instanceOf(WalletBalance.class)).join();
	}
}
