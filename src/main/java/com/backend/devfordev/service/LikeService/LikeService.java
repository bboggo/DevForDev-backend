package com.backend.devfordev.service.LikeService;

import com.backend.devfordev.dto.LikeDto.LikeRequest;
import com.backend.devfordev.dto.LikeDto.LikeResponse;

public interface LikeService {
    public LikeResponse createLike(LikeRequest request, Long userId);
}
