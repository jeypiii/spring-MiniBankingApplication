package com.jbatrina.BankingApplication.exceptions;

import org.springframework.http.HttpStatus;

public class BankingApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int id;
    private String contextMessage;
    private Object contextObject;
    private HttpStatus httpStatus;

    protected BankingApplicationException(String baseMessage, int id, String contextMessage, Object contextObject, HttpStatus httpStatus) {
        super(baseMessage);
        this.id = id;
        this.contextMessage = contextMessage;
        this.contextObject = contextObject;
        this.httpStatus = httpStatus;
    }

    protected BankingApplicationException(String baseMessage, int id, String contextMessage, Object contextObject) {
        this(baseMessage, id, contextMessage, contextObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public BankingApplicationException(int id, String contextMessage, Object contextObject) {
        this("An BankingApplication error has occurred.", id, contextMessage, contextObject);
    }

    public BankingApplicationException(int id, String contextMessage) {
        this(id, contextMessage, null);
    }

    public BankingApplicationException(int id) {
        this(id, "", null);
    }

    // Getters and Setters
    // NOTE: for Setters, we return `this` to enable chaining (like bulilder pattern)
    @Override
    public String getMessage() {
        return String.format("%s%s", super.getMessage(), getExtraInfoMessage(id, contextMessage));
    }

    protected String getExtraInfoMessage(int id, String message) {
        return String.format("(Object ID: %d%s)", id,
            (message.isBlank()) ? "" : String.format("\n\t%s)", message)
        );
    }

    public int getId() {
        return id;
    }

    public BankingApplicationException setId(int id) {
        this.id = id;
        return this;
    }

    public BankingApplicationException setContextMessage(String contextMessage) {
    	return setContextMessage(contextMessage, null);
    }

    public BankingApplicationException setContextMessage(String contextMessage, String identifier) {
    	if (identifier == null) {
    		identifier = "";
    	}

    	assert(identifier != null && (! identifier.startsWith("::")));
    	
    	String fullIdentifier = this.getClass().getSimpleName() + identifier;
    	System.out.println("RETRIEVED exception message FOR " + fullIdentifier);
        this.contextMessage = ExceptionMessageSupplier.getInstance().getErrorMessage(
        		fullIdentifier,  
        		contextMessage
        		);
 
        return this;
    }

    public String getContextMessage() {
        return contextMessage;
    }

    public Object getContextObject() {
        return contextObject;
    }

    public BankingApplicationException setContextObject(Object contextObject) {
        this.contextObject = contextObject;
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public BankingApplicationException setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }
}
