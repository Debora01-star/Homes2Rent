package com.Homes2Rent.Homes2Rent.exceptions;



public class DuplicatedEntryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicatedEntryException(String username) {
        super("Cannot find user " + username);
    }

}










