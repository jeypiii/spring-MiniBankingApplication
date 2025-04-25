package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.User;
import org.springframework.http.HttpStatus;

public class UserInvalidEmailException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserInvalidEmailException(int id, String message, User user) {
        super("The User email provided is not valid", id, message, user);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public UserInvalidEmailException(int id) {
        this(id, "", null);
    }
}
