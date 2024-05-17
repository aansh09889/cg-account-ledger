package com.cg.account.ledger.model;

import java.util.Map;

import com.cg.account.ledger.constants.AccountStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDataModel {

    private String accountId;
    @NotNull(message = "accountStatus is a mandatory field.")
    @NotEmpty(message ="accountStatus is a mandatory field.")
    private AccountStatus accountStatus;
    private Map<String, WalletModel> wallets;
}
