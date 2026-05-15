package com.taehk23.simplechat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

    @Setter
    private Long id;

    private final String username;

    public User(String username) {
        this.username = username;
    }

}
