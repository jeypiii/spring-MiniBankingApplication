package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Transaction;
import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends TransactionException {
    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(int id, String message, Transaction transaction) {
        super("The transaction requested does not exist.", id, message, transaction);
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public TransactionNotFoundException(int id) {
        this(id, "", null);
    }
}
