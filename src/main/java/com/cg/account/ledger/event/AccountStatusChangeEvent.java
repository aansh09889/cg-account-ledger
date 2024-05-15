package com.cg.account.ledger.event;

import com.cg.account.ledger.constants.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountStatusChangeEvent {

    private final String accountId;
    private final AccountStatus status;
}
