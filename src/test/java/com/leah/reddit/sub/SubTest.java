package com.leah.reddit.sub;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import com.leah.reddit.PermissionException;
import com.leah.reddit.Post;
import com.leah.reddit.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SubTest {

    private User founder;
    private Sub cheeseSub;

    @BeforeEach
    public void setup() {
        founder = new User("UserA");
        String name = "cheese";
        cheeseSub = new Sub(name, founder);

    }

    @Test
    public void testCreateSubFounderIsSubAdmin() {
        assertTrue(cheeseSub.hasAdmin(founder));
    }

    @Test
    public void testCreateSubFollowedByFounder() {
        assertTrue(cheeseSub.hasFollower(founder));
    }

    @Test
    public void testCreateSubName() {
        assertEquals(cheeseSub.getName(), "r/cheese");
    }

    @Test
    public void testFollowBy() {
        User follower = new User("CheeseFollower");
        cheeseSub.followBy(follower);
        assertTrue(cheeseSub.hasFollower(follower));

        User nonFollower = new User("NonCheeseFollower");
        assertFalse(cheeseSub.hasFollower(nonFollower));
    }

    @Test
    public void testPostByFollower() throws PermissionException {
        User follower = new User("CheeseFollower");
        cheeseSub.followBy(follower);

        Post post = cheeseSub.post(follower, "PostA");
        assertTrue(cheeseSub.hasPost(post));
    }

    @Test
    public void testPostByNonFollower() {
        User nonFollower = new User("NonFollower");
        assertThrows(PermissionException.class,
                () -> cheeseSub.post(nonFollower, "PostA")
        );
    }

    private Thread createThread(String content) {
        return new Thread(() -> {
            try {
                cheeseSub.post(founder, content);
            } catch (PermissionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @RepeatedTest(10)
    public void testPostConcurrent() throws PermissionException, InterruptedException {
        int count = 2;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Thread thread = createThread(Integer.toString(i));
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }


        assertEquals(cheeseSub.getPosts().get(0).getContent(), "0");
    }


}


