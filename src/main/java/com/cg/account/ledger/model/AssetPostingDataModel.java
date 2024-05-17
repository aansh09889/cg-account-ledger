package com.cg.account.ledger.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetPostingDataModel {

    private String accountId;
    private String postingId;
    @NotBlank(message = "FromWalletId is a required field.")
    @NotEmpty(message ="FromWalletId is a required field.")
    private String fromWalletId;
    @NotBlank(message = "toWalletId is a required field.")
    @NotEmpty(message ="toWalletId is a required field.")
    private String toWalletId;
    @Min(value = 0,message = "Transaction amount must be greater than 0.")
    private BigDecimal txnAmount;
    private LocalDate txnTime;
}
