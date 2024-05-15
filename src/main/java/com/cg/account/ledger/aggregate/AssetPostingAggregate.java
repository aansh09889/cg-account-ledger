package com.cg.account.ledger.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.StringUtils;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cg.account.ledger.command.AssetPostingCommand;
import com.cg.account.ledger.command.AssetPostingStatusChangeCommand;
import com.cg.account.ledger.constants.PostingStatus;
import com.cg.account.ledger.entity.Posting;
import com.cg.account.ledger.event.AssetPostingEvent;
import com.cg.account.ledger.event.AssetPostingStatusChangeEvent;
import com.cg.account.ledger.repository.AssetPostingRepository;

@Aggregate
public class AssetPostingAggregate {

    @AggregateIdentifier
    private String postingId;
    private String accountId;
    private String fromWalletId;
    private String toWalletId;
    private BigDecimal txnAmount;
    private PostingStatus postingStatus;
    private LocalDateTime postingTime;
    
    private static final Logger logger = LoggerFactory.getLogger(AssetPostingAggregate.class);
    
    @Autowired
    AssetPostingRepository assetPostingRepository;
    
    public AssetPostingAggregate() {}
    
    @CommandHandler
    public AssetPostingAggregate(AssetPostingCommand createPostingCommand) {
        logger.info("Asset Posting request came in command handler");
        // Create the Posting Event
        logger.info("Going to post the PostingCreatedEvent...");
        apply(AssetPostingEvent.builder()
        .accountId(createPostingCommand.getAccountId())
        .postingId(UUID.randomUUID().toString())
        .fromWalletId(createPostingCommand.getFromWalletId())
        .toWalletId(createPostingCommand.getToWalletId())
        .postingStatus(PostingStatus.CLEARED)
        .txnAmount(createPostingCommand.getTxnAmount())
        .postingTime(LocalDateTime.now()).build());
    }

    @EventSourcingHandler
    public void on(AssetPostingEvent postingCreatedEvent) {
        logger.info("Asset Posting request came in Event Sourcing ");
        if(postingCreatedEvent != null) {
            this.accountId = postingCreatedEvent.getAccountId();
            this.postingId = postingCreatedEvent.getPostingId();
            this.fromWalletId = postingCreatedEvent.getFromWalletId();
            this.toWalletId = postingCreatedEvent.getToWalletId();
            this.postingStatus = postingCreatedEvent.getPostingStatus();
            this.txnAmount = postingCreatedEvent.getTxnAmount();
            this.postingTime = postingCreatedEvent.getPostingTime();
        }
    }
    
    @CommandHandler
    public void handle(AssetPostingStatusChangeCommand assetPostingStatusChangeCommand) {
    	 logger.info("Asset Update Post request came in command handler");
    	apply(AssetPostingStatusChangeEvent.builder().accountId(assetPostingStatusChangeCommand.getAccountId()).postingId(assetPostingStatusChangeCommand.getPostingId()).postingStatus(assetPostingStatusChangeCommand.getPostingStatus()).build());
    }
    
    @EventSourcingHandler
    public void on(AssetPostingStatusChangeEvent assetPostingStatusChangeEvent) {
        logger.info("Asset Update Post request came in Event Sourcing ");
        if(assetPostingStatusChangeEvent != null) {
            this.accountId = assetPostingStatusChangeEvent.getAccountId();
            this.postingId = assetPostingStatusChangeEvent.getPostingId();
            this.postingStatus = assetPostingStatusChangeEvent.getPostingStatus();
        }
    }
}
