package com.cg.account.ledger.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String errorMessage;
    private LocalDateTime timestamp;
}
