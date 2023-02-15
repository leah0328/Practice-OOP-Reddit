package com.leah.reddit;

public interface Upvotable {

    /**
     * Upvote an object e.g. post, comment
     * @param user User who does upvote
     * @return the current upvote count
     */
    int upvote(User user);

    /**
     * Downvote an object e.g. post, comment
     * @param user User who does upvote
     * @return the current upvote count
     */
    int downvote(User user);

    /**
     *
     * @
     */
}
