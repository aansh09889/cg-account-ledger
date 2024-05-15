package com.cg.account.ledger.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.CryptoType;

@Data
@Builder
public class CryptoDataModel {
    private CryptoType cryptoType;
    private BigDecimal balance;
}
