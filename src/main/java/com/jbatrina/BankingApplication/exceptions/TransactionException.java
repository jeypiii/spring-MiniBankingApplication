package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Transaction;

public class TransactionException extends BankingApplicationException {
    protected TransactionException(String baseMessage, int id, String message, Object contextObject) {
        super(baseMessage, id, message, contextObject);
    }

    public TransactionException(int id, String message, Transaction transaction) {
        this("A transaction-related error has occurred.", id, message, (Object) transaction);
    }

    public TransactionException(int id, String message) {
        this(id, message, null);
    }

    public TransactionException(int id) {
        this(id, "", null);
    }

    public Transaction getTransaction() {
        return (Transaction) getContextObject();
    }

    public TransactionException setTransaction(Transaction transaction) {
        setContextObject((Object) transaction);
        return this;
    }
}
