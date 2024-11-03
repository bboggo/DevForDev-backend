package com.backend.devfordev.service;

import com.backend.devfordev.dto.LikeRequest;
import com.backend.devfordev.dto.LikeResponse;

public interface LikeService {
    public LikeResponse createLike(LikeRequest request, Long userId);
}
