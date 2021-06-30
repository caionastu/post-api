package com.caionastu.postapi.user.application.request;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class UpdateUserRequest {

    @NotBlank(message = "{name.notBlank}")
    @Size(max = 15, message = "{name.size}")
    @Pattern(regexp = "^([a-zA-Z0-9_]+)( [a-zA-Z0-9_]+)*$", message = "{name.invalid}")
    private String name;
}
