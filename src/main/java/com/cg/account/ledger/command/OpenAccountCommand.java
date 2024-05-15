package com.cg.account.ledger.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cg.account.ledger.constants.AccountStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAccountCommand {

    @TargetAggregateIdentifier
    private final String accountId;
    private final AccountStatus accountStatus;
}
