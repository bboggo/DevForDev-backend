package com.backend.devfordev.service;

import com.backend.devfordev.dto.MyPageInfoResponse;

public interface MyPageService {
    public MyPageInfoResponse.ProfileResponse getProfile(Long userId);
}
