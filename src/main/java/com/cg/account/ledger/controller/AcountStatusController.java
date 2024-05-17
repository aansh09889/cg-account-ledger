package com.cg.account.ledger.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.account.ledger.command.AccountStatusCommand;
import com.cg.account.ledger.exception.AccountNotFoundException;
import com.cg.account.ledger.model.AccountDataModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AcountStatusController {

	private static final Logger logger = LoggerFactory.getLogger(AcountStatusController.class);

	@Autowired
	private CommandGateway commandGateway;

	@PatchMapping("/{accountId}/status")
	public ResponseEntity<String> accountStatusChange(@PathVariable(value = "accountId") String accountId,
			@Valid @RequestBody AccountDataModel account) {
		logger.info("accountStatusChange from controller invoked. {}", accountId);

		try {
			return ResponseEntity.ok(commandGateway.sendAndWait(
					AccountStatusCommand.builder().accountId(accountId).status(account.getAccountStatus()).build()));
					
		} catch (AccountNotFoundException e) {
			logger.error("Error at Account Status controller : "+e.getLocalizedMessage());
			return ResponseEntity.notFound().build();
		}

	}

}
