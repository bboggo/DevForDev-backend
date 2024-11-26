package com.backend.devfordev.service;

import com.backend.devfordev.dto.MyPageInfoRequest;
import com.backend.devfordev.dto.MyPageInfoResponse;

public interface MyPageService {
    public MyPageInfoResponse.ProfileResponse getProfile(Long userId);
    public MyPageInfoResponse.ProfileUpdateResponse updateProfile(Long memberId, MyPageInfoRequest.ProfileUpdateRequest request);
    public void updatePassword(Long memberId, MyPageInfoRequest.PasswordUpdateRequest passwordUpdateRequest);
}
