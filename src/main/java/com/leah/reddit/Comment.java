package com.leah.reddit;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Comment implements Commentable, Lockable, Upvotable {
    private final User user;
    private final String content;
    private final UUID id;
    private final List<Comment> comments;
    private boolean locked;
    private int upvoteCount;


    public Comment(User user, String content) {
        this.user = user;
        this.content = content;
        this.id = UUID.randomUUID();
        this.comments = new LinkedList<>();
        this.upvoteCount = 0;
    }


    @Override
    public Comment comment(User user, String content) throws LockedObjectException{
        if (isLocked()) {
            throw new LockedObjectException("Unable to comment on this comment as it is locked");
        }
        Comment childComment = new Comment(user, content);
        comments.add(childComment);
        user.addCommentHistory(childComment);
        return childComment;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void lock(User user) throws PermissionException {
        if (user.isNonAdmin()){
            throw new PermissionException("");
        }
        locked = true;
    }

    @Override
    public void unlock(User user) throws PermissionException {
        if (user.isNonAdmin()){
            throw new PermissionException("");
        }
        locked = false;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    @Override
    public int upvote(User user) {
        upvoteCount += 1;
        return upvoteCount;
    }

    @Override
    public int downvote(User user) {
        upvoteCount -= 1;
        return upvoteCount;
    }
}
