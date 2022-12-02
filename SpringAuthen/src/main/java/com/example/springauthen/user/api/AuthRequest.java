package com.example.springauthen.user.api;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AuthRequest {
    @Length(min = 5,max = 50)
    private String username;

    @Length(min = 5,max = 10)
    private String password;
}
