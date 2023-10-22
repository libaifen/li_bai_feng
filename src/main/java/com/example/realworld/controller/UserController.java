package com.example.realworld.controller;

import com.example.realworld.detail.AuthUserDetails;
import com.example.realworld.dto.UserDto;
import com.example.realworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto currentUser(
            @AuthenticationPrincipal AuthUserDetails authUserDetails
    ){
        return userService.currentUser(authUserDetails);
    }

    @PutMapping
    public UserDto update(
            @Valid @RequestBody UserDto.Update update,
            @AuthenticationPrincipal AuthUserDetails authUserDetails
    ){
        return userService.update(update, authUserDetails);
    }
}
