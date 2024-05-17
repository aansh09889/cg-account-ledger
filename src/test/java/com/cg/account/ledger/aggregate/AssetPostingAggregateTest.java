package com.cg.account.ledger.aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cg.account.ledger.command.AssetPostingCommand;
import com.cg.account.ledger.command.AssetPostingStatusChangeCommand;
import com.cg.account.ledger.constants.PostingStatus;
import com.cg.account.ledger.event.AssetPostingEvent;
import com.cg.account.ledger.event.AssetPostingStatusChangeEvent;

public class AssetPostingAggregateTest {

	private FixtureConfiguration<AssetPostingAggregate> fixture;

	@BeforeEach
	public void setup() {
		System.out.println("inside");
		fixture = new AggregateTestFixture<>(AssetPostingAggregate.class);
	}

	@Test
	public void testAssetPostAggregate() {
		String postingId = UUID.randomUUID().toString();
		String accountId = UUID.randomUUID().toString();
		String fromWalletId = UUID.randomUUID().toString();
		String toWalletId = UUID.randomUUID().toString();
		BigDecimal txnAmount = new BigDecimal(25);
		fixture.givenNoPriorActivity().when(new AssetPostingCommand(postingId, accountId, fromWalletId, toWalletId,
				txnAmount, PostingStatus.PENDING, null)).expectSuccessfulHandlerExecution();
		// .expectEvents(new
		// AssetPostingEvent(postingId,accountId,fromWalletId,toWalletId,txnAmount,PostingStatus.CLEARED,LocalDateTime.now())).expectException(Exception.class);
	}

	@Test
	public void testAssetPostUpdateAggregate() {
		String postingId = UUID.randomUUID().toString();
		String accountId = UUID.randomUUID().toString();
		String fromWalletId = UUID.randomUUID().toString();
		String toWalletId = UUID.randomUUID().toString();
		BigDecimal txnAmount = new BigDecimal(25);
		fixture.given(new AssetPostingEvent(postingId, accountId, fromWalletId, toWalletId,
				txnAmount, PostingStatus.PENDING, null))
				.when(new AssetPostingStatusChangeCommand(postingId, accountId, PostingStatus.PENDING)).expectEvents(new AssetPostingStatusChangeEvent(postingId, accountId, PostingStatus.PENDING));
	}
}
