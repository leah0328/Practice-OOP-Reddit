package com.leah.reddit;

public class LockedObjectException extends Exception{
    public LockedObjectException(String errorMessage) {
        super(errorMessage);
    }
}
