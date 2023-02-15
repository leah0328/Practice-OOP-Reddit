package com.leah.reddit;

import java.util.UUID;

public class Share {
    private final UUID id;
    private final String content;
    private final User user;

    public Share(User user, String content) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.content = content;
    }


}
