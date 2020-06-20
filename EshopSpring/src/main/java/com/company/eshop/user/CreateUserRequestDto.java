package com.company.eshop.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {
    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
}
