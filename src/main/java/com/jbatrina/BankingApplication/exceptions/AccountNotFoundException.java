package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Account;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends AccountException {
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(int id, String message, Account account) {
        super("The account requested does not exist.", id, message, account);
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public AccountNotFoundException(int id) {
        this(id, "", null);
    }
}
