package com.leah.reddit;

public class PermissionException extends Exception{
    public PermissionException(String errorMessage) {
        super(errorMessage);
    }
}

