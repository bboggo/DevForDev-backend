package com.backend.devfordev.service;


import com.backend.devfordev.dto.SignInRequest;
import com.backend.devfordev.dto.SignInResponse;
import com.backend.devfordev.dto.SignUpRequest;
import com.backend.devfordev.dto.SignUpResponse;

public interface MemberService {
    public SignUpResponse signUp(SignUpRequest request);
    public SignInResponse signIn(SignInRequest request);
}
