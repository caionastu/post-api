package com.caionastu.postapi.user.application.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class CreateUserRequest {

    @NotBlank(message = "{name.notBlank}")
    @Size(max = 15, message = "{name.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$", message = "{name.invalid}")
    private String name;

    @Email(message = "{email.badFormat}")
    @NotBlank(message = "{email.notBlank}")
    private String email;
}
