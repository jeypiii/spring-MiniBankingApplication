package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.User;
import org.springframework.http.HttpStatus;

public class AuthUserExistsException extends AuthException {
    private static final long serialVersionUID = 1L;

    public AuthUserExistsException(int id, String message, Object contextObject) {
        super("The identifier/credentials for the user already exists", id, message, contextObject);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }

    public AuthUserExistsException(User user) {
        this(-1, "", user);
    }

    public User getUser() {
        return (User) getContextObject();
    }

    public AuthUserExistsException setUser(User user) {
        setContextObject((Object) user);
        return this;
    }
}
