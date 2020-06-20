package com.company.eshop.user;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoMapper {

    private Function<User, UserResponseDto> toResponse = user -> UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFirstname() + " " + user.getLastname())
                .build();

    private Function<CreateUserRequestDto, User> fromRequest = userRequest -> User.builder()
            .email(userRequest.getEmail())
            .username(userRequest.getUsername())
            .password(userRequest.getPassword())
            .firstname(userRequest.getFirstname())
            .lastname(userRequest.getLastname())
            .build();


    public User fromRequest(CreateUserRequestDto requestDto) {
        return fromRequest.apply(requestDto);
    }


    public UserResponseDto fromUser(User user) {
        return toResponse.apply(user);
    }

}
