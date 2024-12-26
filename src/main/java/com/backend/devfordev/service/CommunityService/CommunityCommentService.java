package com.backend.devfordev.service.CommunityService;

import com.backend.devfordev.dto.CommunityDto.CommunityCommentRequest;
import com.backend.devfordev.dto.CommunityDto.CommunityCommentResponse;

public interface CommunityCommentService {
    public CommunityCommentResponse addComment(Long comId, Long userId, CommunityCommentRequest request);
}
