package com.cg.account.ledger.event;

import java.util.HashMap;
import java.util.Map;

import com.cg.account.ledger.constants.AccountStatus;
import com.cg.account.ledger.model.WalletModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class AccountOpenedEvent {

    private String accountId;
    private AccountStatus status;
    private Map<String, WalletModel> wallets = new HashMap<>();
}
