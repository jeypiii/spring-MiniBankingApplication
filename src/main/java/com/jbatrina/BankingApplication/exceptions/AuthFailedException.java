package com.jbatrina.BankingApplication.exceptions;

public class AuthFailedException extends AuthException {
    private static final long serialVersionUID = 1L;

    public AuthFailedException(int id, String message, Object contextObject) {
        super("Authentication failed.", id, message, contextObject);
    }

    public AuthFailedException() {
        this(-1, "", null);
    }
}
