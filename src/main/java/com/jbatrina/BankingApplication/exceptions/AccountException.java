package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Account;

public class AccountException extends BankingApplicationException {
    protected AccountException(String baseMessage, int id, String message, Object contextObject) {
        super(baseMessage, id, message, contextObject);
    }

    public AccountException(int id, String message, Account account) {
        this("A account-related error has occurred.", id, message, (Object) account);
    }

    public AccountException(int id, String message) {
        this(id, message, null);
    }

    public AccountException(int id) {
        this(id, "", null);
    }

    public Account getAccount() {
        return (Account) getContextObject();
    }

    public AccountException setAccount(Account account) {
        setContextObject((Object) account);
        return this;
    }
}
