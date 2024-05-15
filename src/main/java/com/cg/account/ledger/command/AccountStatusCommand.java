package com.cg.account.ledger.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cg.account.ledger.constants.AccountStatus;

@Data
@Builder
public class AccountStatusCommand {
    @TargetAggregateIdentifier
    private final String accountId;
    private final AccountStatus status;
}
