package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Transaction;
import org.springframework.http.HttpStatus;

public class TransactionIdConflictException extends TransactionException {
    private static final long serialVersionUID = 1L;

    public TransactionIdConflictException(int id, String message, Transaction Transaction) {
        super("A Transaction with the same ID already exists.", id, message, Transaction);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public TransactionIdConflictException(int id) {
        this(id, "", null);
    }
}
