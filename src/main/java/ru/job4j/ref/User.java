package ru.job4j.ref;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String name;

    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }
}
