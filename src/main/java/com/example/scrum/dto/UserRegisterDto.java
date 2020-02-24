package com.example.scrum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String repeatedPassword;

}
