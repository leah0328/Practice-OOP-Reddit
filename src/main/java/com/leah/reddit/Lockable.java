package com.leah.reddit;

public interface Lockable {

    boolean isLocked();

    void lock(User user) throws PermissionException;

    void unlock(User user) throws PermissionException;

}
