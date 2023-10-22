package com.example.realworld.service;

import com.example.realworld.detail.AuthUserDetails;
import com.example.realworld.dto.ProfileDto;
import com.example.realworld.entity.FollowEntity;
import com.example.realworld.entity.UserEntity;
import com.example.realworld.ex.AppException;
import com.example.realworld.repository.FollowRepository;
import com.example.realworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.realworld.ex.Error;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    public ProfileDto getProfile(String name, AuthUserDetails authUserDetails) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        Boolean following = followRepository.findByFolloweeIdAndFollowerId(user.getId(), authUserDetails.getId()).isPresent();

        return convertToProfile(user, following);
    }

    @Override
    public ProfileDto getProfileByUserId(Long userId, AuthUserDetails authUserDetails) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        Boolean following = followRepository.findByFolloweeIdAndFollowerId(user.getId(), authUserDetails.getId()).isPresent();

        return convertToProfile(user, following);
    }

    @Transactional
    @Override
    public ProfileDto followUser(String name, AuthUserDetails authUserDetails) {
        UserEntity followee = userRepository.findByUsername(name).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        UserEntity follower = UserEntity.builder().id(authUserDetails.getId()).build(); // myself

        followRepository.findByFolloweeIdAndFollowerId(followee.getId(), follower.getId())
                .ifPresent(follow -> {throw new AppException(Error.ALREADY_FOLLOWED_USER);});

        FollowEntity follow =  FollowEntity.builder().followee(followee).follower(follower).build();
        followRepository.save(follow);

        return convertToProfile(followee, true);
    }

    @Transactional
    @Override
    public ProfileDto unfollowUser(String name, AuthUserDetails authUserDetails) {
        UserEntity followee = userRepository.findByUsername(name).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        UserEntity follower = UserEntity.builder().id(authUserDetails.getId()).build(); // myself

        FollowEntity follow = followRepository.findByFolloweeIdAndFollowerId(followee.getId(), follower.getId())
                .orElseThrow(() -> new AppException(Error.FOLLOW_NOT_FOUND));
        followRepository.delete(follow);

        return convertToProfile(followee, false);
    }

    private ProfileDto convertToProfile(UserEntity user, Boolean following) {
        return ProfileDto.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .following(following)
                .build();
    }
}