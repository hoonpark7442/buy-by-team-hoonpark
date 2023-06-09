package com.flab.bbt.user.controller;

import com.flab.bbt.common.CommonResponse;
import com.flab.bbt.common.SessionConst;
import com.flab.bbt.common.annotation.CurrentUser;
import com.flab.bbt.exception.CustomException;
import com.flab.bbt.exception.ErrorCode;
import com.flab.bbt.user.domain.User;
import com.flab.bbt.user.domain.UserProfile;
import com.flab.bbt.user.request.UpdateUserRequest;
import com.flab.bbt.user.request.UserProfileRequest;
import com.flab.bbt.user.response.UserResponse;
import com.flab.bbt.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/{userId}/user-profiles")
    public CommonResponse createUserProfile(@PathVariable Long userId,
        @RequestBody UserProfileRequest userProfileRequest, @CurrentUser User currentUser) {

        if (currentUser.getId() != userId) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        currentUser.updateUserProfile(userProfileRequest.convertToUserProfile(userId));
        User userWithCreatedProfile = userService.createUserProfile(currentUser);

        return CommonResponse.success(
            UserResponse.convertToUserResponse(userWithCreatedProfile));
    }

    @PatchMapping("/user-profiles")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse updateUserProfiile(@RequestBody UpdateUserRequest updateUserRequest,
        @CurrentUser User currentUser) {
        UserProfile userProfile = updateUserRequest.convertToUserProfile();

        User userWithUpdatedProfile = userService.updateUserProfile(currentUser,
            updateUserRequest.convertToUserProfile());

        return CommonResponse.success(
            UserResponse.convertToUserResponse(userWithUpdatedProfile));
    }
}
