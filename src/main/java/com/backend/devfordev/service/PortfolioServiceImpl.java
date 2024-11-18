package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;

import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;

import com.backend.devfordev.apiPayload.exception.handler.PortfolioHandler;
import com.backend.devfordev.converter.PortfolioConverter;

import com.backend.devfordev.domain.*;

import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;
import com.backend.devfordev.repository.*;
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
    private final PortfolioEducationRepository portfolioEducationRepository;
    private final PortfolioAwardRepository portfolioAwardRepository;

    @Override
    @Transactional
    public PortfolioResponse.PortCreateResponse createPortfolio(PortfolioRequest.PortfolioCreateRequest request, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));

        // 포트폴리오 생성
        Portfolio portfolio = PortfolioConverter.toPortfolio(request, member);
        portfolioRepository.save(portfolio);

        // 링크 리스트 순서 자동 설정 후 변환 및 저장
        List<PortfolioLink> links = PortfolioConverter.toPortfolioLinks(request.getLinks(), portfolio);
        for (int i = 0; i < links.size(); i++) {
            links.get(i).setOrderIndex(i + 1); // 자동 순서 설정
        }
        portfolioLinkRepository.saveAll(links);

        // 학력 리스트 순서 자동 설정 후 변환 및 저장
        List<PortfolioEducation> educations = PortfolioConverter.toEducationList(request.getEducations(), portfolio);
        for (int i = 0; i < educations.size(); i++) {
            educations.get(i).setOrderIndex(i + 1); // 자동 순서 설정
        }
        portfolioEducationRepository.saveAll(educations);

        // 수상 및 기타 리스트 순서 자동 설정 후 변환 및 저장
        List<PortfolioAward> awards = PortfolioConverter.toAwardList(request.getAwards(), portfolio);
        for (int i = 0; i < awards.size(); i++) {
            awards.get(i).setOrderIndex(i + 1); // 자동 순서 설정
        }
        portfolioAwardRepository.saveAll(awards);


        // 포트폴리오 응답 변환
        return PortfolioConverter.toPortfolioResponse(portfolio, links, educations, awards);
    }


}
