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


        // order 값 예외 처리
        List<Integer> orderList = request.getLinks().stream()
                .map(PortfolioRequest.PortfolioCreateRequest.LinkRequest::getOrder)
                .filter(Objects::nonNull) // null 값 필터링
                .sorted()
                .collect(Collectors.toList());

        // 1. 모든 `order` 값이 존재하고 음수가 아니어야 함
        if (orderList.size() != request.getLinks().size() || orderList.get(0) <= 0) {
            throw new PortfolioHandler(ErrorStatus.INVALID_ORDER_ERROR);
        }

        // 2. `order` 값이 연속적이어야 함 (1, 2, 3,... 형식)
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i) != i + 1) {
                throw new PortfolioHandler(ErrorStatus.INVALID_ORDER_ERROR);
            }
        }

        Portfolio portfolio = PortfolioConverter.toPortfolio(request, member);

        portfolioRepository.save(portfolio);
        List<PortfolioLink> links = PortfolioConverter.toPortfolioLinks(request.getLinks(), portfolio);
        portfolioLinkRepository.saveAll(links);

        return PortfolioConverter.toPortfolioResponse(portfolio, links);
    }


}
