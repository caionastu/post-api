package com.caionastu.postapi.user.domain;

import com.caionastu.postapi.user.application.request.CreateUserRequest;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private boolean active;

    public static User from(CreateUserRequest request) {
        User user = new User();
        user.name = request.getName();
        user.email = request.getEmail();
        user.active = true;
        return user;
    }
}
