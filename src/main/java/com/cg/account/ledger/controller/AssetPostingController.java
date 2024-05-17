package com.cg.account.ledger.controller;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.account.ledger.command.AssetPostingCommand;
import com.cg.account.ledger.command.AssetPostingStatusChangeCommand;
import com.cg.account.ledger.model.AssetPostingDataModel;
import com.cg.account.ledger.model.AssetPostingStatusChangeModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AssetPostingController {

	@Autowired
	private CommandGateway commandGateway;

	private static final Logger logger = LoggerFactory.getLogger(AssetPostingController.class);

	@PostMapping("/{accountId}/posting")
	public ResponseEntity createPosting(@PathVariable(value = "accountId") String accountId,
			@Valid @RequestBody List<AssetPostingDataModel> assetPostingDataModel) {
		logger.info("Request received in Posting controller for account id :" + accountId);
		assetPostingDataModel.stream()
				.forEach(assetPosting -> commandGateway.send(AssetPostingCommand.builder()
						.accountId(assetPosting.getAccountId()).fromWalletId(assetPosting.getFromWalletId())
						.toWalletId(assetPosting.getToWalletId()).txnAmount(assetPosting.getTxnAmount()).build()));
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/{accountId}/posting")
	public ResponseEntity updatePostingStatus(@PathVariable(value = "accountId") String accountId,
			@Valid @RequestBody List<AssetPostingStatusChangeModel> assetPostingStatusChangeModel) {
		logger.info("Request received to update the status of the post in  controller for account id :" + accountId);
		assetPostingStatusChangeModel.stream()
				.forEach(assetPostingStatusChange -> commandGateway.sendAndWait(
						AssetPostingStatusChangeCommand.builder().postingId(assetPostingStatusChange.getPostingId())
								.accountId(assetPostingStatusChange.getAccountId())
								.postingStatus(assetPostingStatusChange.getPostingStatus()).build()));
		return ResponseEntity.accepted().build();
	}
}
