package com.leah.reddit;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Post implements Commentable, Lockable, Upvotable, Shareable {
    private final UUID id;
    private String content;
    private final UUID owner;
    private int upvoteCount;
    private boolean locked;

    private final List<Comment> comments;

    private final List<Share> shares;

    public Post(User user, String content) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.owner = user.getId();
        this.upvoteCount = 0;
        this.comments = new LinkedList<>();
        this.upvoteCount = 0;
        this.shares = new LinkedList<>();
    }

    public Comment comment(User user, String content) throws LockedObjectException {
        if (isLocked()) {
            throw new LockedObjectException("Unable to comment on this post as it is locked");
        }
        Comment comment = new Comment(user, content);
        comments.add(comment);
        return comment;
    }

    public List<Comment> listComments() {
        return comments;
    }


    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void lock(User user) throws PermissionException {
        if (user.isNonAdmin()){
            throw new PermissionException("No permission to lock post");
        }
        locked = true;
    }

    @Override
    public void unlock(User user) throws PermissionException {
        if (user.isNonAdmin()){
            throw new PermissionException("No permission to unlock post");
        }
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

    public String getContent() {
        return content;
    }

    public Share share(User user, String content) {
        Share share = new Share(user, content);
        shares.add(share);
        user.addSharesHistory(share);
        return share;
    }
}

