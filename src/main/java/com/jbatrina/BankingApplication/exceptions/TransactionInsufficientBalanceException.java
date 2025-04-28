package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Transaction;
import org.springframework.http.HttpStatus;

public class TransactionInsufficientBalanceException extends TransactionException {
    private static final long serialVersionUID = 1L;

    public TransactionInsufficientBalanceException(int id, String message, Transaction Transaction) {
        super("The Source account has insufficient balance for this transaction", id, message, Transaction);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public TransactionInsufficientBalanceException(int id) {
        this(id, "", null);
    }
}
