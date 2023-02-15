package com.leah.reddit;

public class Main {
    public static void main(String[] args) {


//        Lockable post = sub1.post(userA, "newContent");
//        System.out.println("Hello world!");
          User userA = new User("dummy");
            Post post = new Post(userA,"Thid is my first post");
//        //Comment comment = userA.commentPost(post, "HI");
//        Comment comment = post.comment(userA, "Hi");

//        //userB.commentPost(post, "hello");
//        //Comment
//        List<Comment> comments = post.listComments();
//        //userB.replyComment(comment, "replytoA");
//        Comment replyToAComment = comment.comment(userB, "replytoA");
        Share share = post.share(userA, "content");


    }
}






