package com.backend.devfordev.service;


import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;

public interface MemberService {
    public SignUpResponse registMember(SignUpRequest request);
}
