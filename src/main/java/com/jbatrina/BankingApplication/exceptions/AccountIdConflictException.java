package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.Account;
import org.springframework.http.HttpStatus;

public class AccountIdConflictException extends AccountException {
    private static final long serialVersionUID = 1L;

    public AccountIdConflictException(int id, String message, Account Account) {
        super("An Account with the same ID already exists.", id, message, Account);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public AccountIdConflictException(int id) {
        this(id, "", null);
    }
}
