package com.cg.account.ledger.projection;

import java.math.BigDecimal;

import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.account.ledger.entity.Wallet;
import com.cg.account.ledger.model.GetWalletBalanceQuery;
import com.cg.account.ledger.model.WalletBalance;
import com.cg.account.ledger.repository.WalletRepository;
import com.cg.account.ledger.util.AccountLedgerUtil;

@Component
public class WalletBalanceProjection {

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	AccountLedgerUtil accountLedgerUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(WalletBalanceProjection.class);

	public WalletBalanceProjection() {
	};

	@QueryHandler
	public WalletBalance handle(GetWalletBalanceQuery balanceQuery) {

		logger.info("Get Query for balance for wallet ID : "+balanceQuery.getWalletid());
		Wallet wallet = walletRepository.findByWalletId(balanceQuery.getWalletid());
		BigDecimal amount = accountLedgerUtil.getAmountFromAsset(wallet);
		logger.info("Get Query for balance completed for wallet ID : "+balanceQuery.getWalletid());
		return WalletBalance.builder().walletid(balanceQuery.getWalletid()).assetType(wallet.getAssetType().toString())
				.balance(amount.toString()).build();
	}
}
