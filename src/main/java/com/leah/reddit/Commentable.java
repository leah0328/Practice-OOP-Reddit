package com.leah.reddit;

public interface Commentable {

    Comment comment(User user, String content) throws LockedObjectException;


}
