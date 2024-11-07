package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;

import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;

import com.backend.devfordev.apiPayload.exception.handler.PortfolioHandler;
import com.backend.devfordev.converter.PortfolioConverter;

import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.Portfolio;

import com.backend.devfordev.domain.PortfolioLink;
import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;
import com.backend.devfordev.repository.MemberRepository;
import com.backend.devfordev.repository.PortfolioLinkRepository;
import com.backend.devfordev.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService{
    private final PortfolioRepository portfolioRepository;
    private final PortfolioLinkRepository portfolioLinkRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public PortfolioResponse.PortCreateResponse createPortfolio(PortfolioRequest.PortfolioCreateRequest request, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));



        Portfolio portfolio = PortfolioConverter.toPortfolio(request, member);


        // 링크 리스트 순서 자동 설정 후 저장
        List<PortfolioLink> links = new ArrayList<>();
        for (int i = 0; i < request.getLinks().size(); i++) {
            PortfolioRequest.PortfolioCreateRequest.LinkRequest linkRequest = request.getLinks().get(i);
            PortfolioLink link = PortfolioLink.builder()
                    .type(linkRequest.getType())
                    .url(linkRequest.getUrl())
                    .orderIndex(i + 1)  // 리스트의 인덱스를 기반으로 순서 설정
                    .portfolio(portfolio)
                    .build();
            links.add(link);
        }


        portfolioRepository.save(portfolio);
        portfolioLinkRepository.saveAll(links);

        return PortfolioConverter.toPortfolioResponse(portfolio, links);
    }


}
