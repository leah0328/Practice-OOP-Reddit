package com.leah.reddit.sub;

import com.leah.reddit.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Sub implements Shareable {
    private final UUID id;
    private final String name;
    private final List<Post> posts;

    private final List<User> admins;
    private final Set<User> followers;
    private final Map<UUID, List<Post>> userPosts;

    private final List<Share> shareHistory;

    public Sub(String name, User founder) {
        this.id = UUID.randomUUID();
        this.name = "r/" + name;
        this.posts = new LinkedList<>();
        this.admins = new ArrayList<>();
        this.followers = ConcurrentHashMap.newKeySet();
        this.admins.add(founder);
        this.followBy(founder);
        this.userPosts = new ConcurrentHashMap<>();
        this.shareHistory = new LinkedList<>();
    }

    public void followBy(User user) {
        followers.add(user);
        user.followSub(this);
    }

    synchronized public Post post(User user, String content) throws PermissionException {
        if (!hasFollower(user)) {
            throw new PermissionException("");
        }
        Post post = new Post(user, content);
        posts.add(post);
        user.addPostHistory(post);

        UUID userId = user.getId();
        userPosts.computeIfAbsent(user.getId(), k -> {
            List<Post> posts = new ArrayList<>();
            posts.add(post);
            return posts;
        });
        userPosts.computeIfPresent(user.getId(), (k, v) -> {
            v.add(post);
            return v;


        });


        if (userPosts.containsKey(userId)) {
            var posts = userPosts.get(userId);
            posts.add(post);
        } else {
            var posts = new ArrayList<Post>();
            posts.add(post);
            userPosts.put(userId, posts);
        }



        return post;
    }

    public boolean hasPost(Post post) {
        return this.posts.contains(post);
    }

    public boolean hasFollower(User user) {
        return this.followers.contains(user);
    }

    public boolean hasAdmin(User user) {
        return this.admins.contains(user);
    }

    public String getName() {
        return name;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public Share share(User user, String content) {
        Share share = new Share(user, content);
        this.shareHistory.add(share);
        user.addSharedHistory(share);
        return share;
    }
}
