package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.User;
import org.springframework.http.HttpStatus;

public class UserIdMismatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserIdMismatchException(int id, String message, User user) {
        super("The User ID does not match expected user.", id, message, user);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public UserIdMismatchException(int id) {
        this(id, "", null);
    }
}
