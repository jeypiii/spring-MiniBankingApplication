package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.User;
import org.springframework.http.HttpStatus;

public class AuthException extends BankingApplicationException {
    protected AuthException(String baseMessage, int id, String message, Object contextObject) {
        super(baseMessage, id, message, contextObject);
        this.setHttpStatus(HttpStatus.UNAUTHORIZED);
    }

    public AuthException(int id, String message, User user) {
        this("A user-related error has occurred.", id, message, (Object) user);
    }

    public AuthException(int id, String message) {
        this(id, message, null);
    }

    public AuthException(int id) {
        this(id, "", null);
    }

    public User getUser() {
        return (User) getContextObject();
    }

    public AuthException setUser(User user) {
        setContextObject((Object) user);
        return this;
    }
}
