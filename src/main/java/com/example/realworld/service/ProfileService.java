package com.example.realworld.service;

import com.example.realworld.detail.AuthUserDetails;
import com.example.realworld.dto.ProfileDto;

public interface ProfileService {
    ProfileDto getProfile(final String username, final AuthUserDetails authUserDetails);
    ProfileDto getProfileByUserId(Long userId, AuthUserDetails authUserDetails);

    ProfileDto followUser(String name, AuthUserDetails authUserDetails);

    ProfileDto unfollowUser(String name, AuthUserDetails authUserDetails);
}