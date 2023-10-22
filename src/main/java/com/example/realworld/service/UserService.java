package com.example.realworld.service;


import com.example.realworld.detail.AuthUserDetails;
import com.example.realworld.dto.UserDto;

public interface UserService {
    UserDto registration(final UserDto.Registration registration);

    UserDto login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);
}