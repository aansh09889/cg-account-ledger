package com.cg.account.ledger.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AccountLedgerErrorHandler {


    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException runtimeException,WebRequest request) {
        return new ResponseEntity<ErrorMessage>(ErrorMessage.builder().errorMessage(runtimeException.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleOtherException(Exception ex,WebRequest request) {
        return new ResponseEntity<ErrorMessage>(ErrorMessage.builder().errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}