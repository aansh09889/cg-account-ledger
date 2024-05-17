package com.cg.account.ledger.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.cg.account.ledger.aggregate.AccountAggregate;
import com.cg.account.ledger.command.AccountStatusCommand;
import com.cg.account.ledger.constants.AccountStatus;
import com.cg.account.ledger.model.AccountDataModel;

@RunWith(MockitoJUnitRunner.class)
public class AccountStatusControllerTest {

	
	@InjectMocks
	AcountStatusController acountStatusController;
	
	private FixtureConfiguration<AccountAggregate> fixture;
	
	private CommandGateway commandGateway;
	
	@BeforeEach
	public void setup() {
		System.out.println("inside");
		this.fixture=new AggregateTestFixture<>(AccountAggregate.class);
		this.commandGateway=mock(CommandGateway.class);
		fixture.registerInjectableResource(commandGateway);
		this.acountStatusController=new AcountStatusController();
	}
	
	@Test
	public void accountStatusChangeTest() {
		String accountId=UUID.randomUUID().toString();
		AccountDataModel accountDataModel=AccountDataModel.builder().accountId(accountId).accountStatus(AccountStatus.CLOSED).wallets(null).build();
		when(commandGateway.sendAndWait(Mockito.<AccountStatusCommand>any())).thenReturn("Success");
		//acountStatusController.accountStatusChange(accountId, accountDataModel);
	}
	
	
}
