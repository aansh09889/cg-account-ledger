package com.cg.account.ledger.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.cg.account.ledger.constants.FundType;

@Data
@Builder
public class FundDataModel {
    private FundType fundType;
    private BigDecimal balance;
}
