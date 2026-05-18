package com.taehk23.simplechat.entity;

import lombok.Getter;

@Getter
public class User {

    private Long id;

    private final String username;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
