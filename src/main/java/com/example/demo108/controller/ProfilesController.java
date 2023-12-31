package com.example.demo108.controller;

import com.example.demo108.detail.AuthUserDetails;
import com.example.demo108.dto.ProfileDto;
import com.example.demo108.service.ProfileService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfilesController {
    private final ProfileService profileService;

    @GetMapping("/test1/test2")
    public String test() {
        return "hhh";
    }

    @GetMapping("/{username}")
    public ProfileDto.Single getProfile(@PathVariable("username") String name, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ProfileDto.Single(profileService.getProfile(name, authUserDetails));
    }

    @PostMapping("/{username}/follow")
    public ProfileDto.Single followUser(@PathVariable("username") String name, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ProfileDto.Single(profileService.followUser(name, authUserDetails));
    }

    @DeleteMapping("/{username}/follow")
    public ProfileDto.Single unfollowUser(@PathVariable("username") String name, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ProfileDto.Single(profileService.unfollowUser(name, authUserDetails));
    }

}