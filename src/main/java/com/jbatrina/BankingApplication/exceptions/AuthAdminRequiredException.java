package com.jbatrina.BankingApplication.exceptions;

import com.jbatrina.BankingApplication.entity.User;
import org.springframework.http.HttpStatus;

public class AuthAdminRequiredException extends AuthException {
    private static final long serialVersionUID = 1L;

    public AuthAdminRequiredException(int id, String message, Object contextObject) {
        super("This action requires admin permissions", id, message, contextObject);
        this.setHttpStatus(HttpStatus.FORBIDDEN);
    }

    public AuthAdminRequiredException() {
        this(-1, "", null);
    }

    public User getUser() {
        return (User) getContextObject();
    }

    public AuthAdminRequiredException setUser(User user) {
        setContextObject((Object) user);
        return this;
    }
}
