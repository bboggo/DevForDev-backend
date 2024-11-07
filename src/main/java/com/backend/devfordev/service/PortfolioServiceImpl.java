package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;

import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;

import com.backend.devfordev.converter.PortfolioConverter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.Portfolio;

import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;
import com.backend.devfordev.repository.MemberRepository;
import com.backend.devfordev.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService{
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public PortfolioResponse.PortCreateResponse createPortfolio(PortfolioRequest.PortfolioCreateRequest request, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));


        Portfolio portfolio = PortfolioConverter.toPortfolio(request, member);

        portfolioRepository.save(portfolio);

        return PortfolioConverter.toPortfolioResponse(portfolio);
    }


}
