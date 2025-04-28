package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Account;
import org.springframework.http.HttpStatus;

public class AccountIsClosedException extends AccountException {
    private static final long serialVersionUID = 1L;

    public AccountIsClosedException(int id, String message, Account Account) {
        super("The account has already been closed", id, message, Account);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public AccountIsClosedException(int id) {
        this(id, "", null);
    }
}
