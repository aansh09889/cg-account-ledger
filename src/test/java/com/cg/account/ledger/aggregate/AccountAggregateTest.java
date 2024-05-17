package com.cg.account.ledger.aggregate;

import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cg.account.ledger.command.AccountStatusCommand;
import com.cg.account.ledger.constants.AccountStatus;
import com.cg.account.ledger.event.AccountOpenedEvent;
import com.cg.account.ledger.event.AccountStatusChangeEvent;


public class AccountAggregateTest {

	private FixtureConfiguration<AccountAggregate> fixture;

	@BeforeEach
	public void setup() {
		System.out.println("inside");
		fixture = new AggregateTestFixture<>(AccountAggregate.class);
	}

	@Test
	public void testAccountStatusChangeAggregate() {
		String accountId1 = UUID.randomUUID().toString();
		fixture.given(new AccountOpenedEvent(accountId1, AccountStatus.CLOSED, null))
				.when(new AccountStatusCommand(accountId1, AccountStatus.OPEN))
				.expectEvents(new AccountStatusChangeEvent(accountId1, AccountStatus.OPEN));
	}
}
