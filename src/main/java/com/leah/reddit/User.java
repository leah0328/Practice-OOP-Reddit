package com.leah.reddit;

import com.leah.reddit.sub.Sub;

import java.util.*;

public class User {
    private final UUID id;
    private final List<Post> postHistory;
    private final List<Comment> commentsHistory;
    private final Set<Sub> followedSubs;
    private boolean isAdmin;
    private final String userName;

    private final List<Share> sharesHistory;

    public User(String userName) {
        this.userName = userName;
        this.id = UUID.randomUUID();
        this.postHistory = new LinkedList<>();
        this.commentsHistory = new LinkedList<>();
        this.isAdmin = false;
        this.followedSubs = new HashSet<>();
        this.sharesHistory = new LinkedList<>();
    }

    public User(boolean isAdmin, String userName) {
        this(userName);
        this.isAdmin = isAdmin;
    }

    public UUID getId() {
        return id;
    }

    public void addPostHistory(Post post) {
        postHistory.add(post);
    }

    public List<Post> getPostHistory() {
        return postHistory;
    }


    public boolean isNonAdmin() {
        return !isAdmin;
    }

    public void setAdmin(User user, boolean isAdmin) throws PermissionException {
        if (isNonAdmin()) {
            throw new PermissionException("");
        }
        this.isAdmin = isAdmin;
    }

    public List<Comment> listComments() {
        return commentsHistory;
    }

    public void addCommentHistory(Comment comment) {
        commentsHistory.add(comment);
    }

    public void followSub(Sub sub) {
        this.followedSubs.add(sub);
    }

    @Override
    public String toString() {
        return this.userName;
    }

    public void addSharesHistory(Share share) {
        this.sharesHistory.add(share);
    }
}
