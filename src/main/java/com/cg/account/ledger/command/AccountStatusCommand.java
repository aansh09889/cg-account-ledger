package com.cg.account.ledger.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cg.account.ledger.constants.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountStatusCommand {
    @TargetAggregateIdentifier
    private final String accountId;
    private final AccountStatus status;
}
