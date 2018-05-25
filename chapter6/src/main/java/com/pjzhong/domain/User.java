package com.pjzhong.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
