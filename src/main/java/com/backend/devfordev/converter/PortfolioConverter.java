package com.backend.devfordev.converter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.Portfolio;

import com.backend.devfordev.domain.PortfolioLink;
import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PortfolioConverter {

    public static Portfolio toPortfolio(PortfolioRequest.PortfolioCreateRequest request, Member member) {

        return Portfolio.builder()
                .portTitle(request.getPortTitle())
                .portContent(request.getPortContent())
                .portImageUrl(request.getPortImageUrl())
                .member(member)
                .build();
    }

    public static List<PortfolioLink> toPortfolioLinks(List<PortfolioRequest.PortfolioCreateRequest.LinkRequest> linkRequests, Portfolio portfolio) {
        return linkRequests.stream()
                .map(linkRequest -> PortfolioLink.builder()
                        .type(linkRequest.getType())
                        .url(linkRequest.getUrl())
                        .orderIndex(linkRequest.getOrder())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());
    }

    public static PortfolioResponse.PortCreateResponse toPortfolioResponse(Portfolio portfolio,  List<PortfolioLink> links) {
        List<PortfolioResponse.PortCreateResponse.LinkResponse> linkResponses = links.stream()
                .map(link -> new PortfolioResponse.PortCreateResponse.LinkResponse(link.getType(), link.getUrl(), link.getOrderIndex()))
                .collect(Collectors.toList());

        return new PortfolioResponse.PortCreateResponse(
                portfolio.getId(),
                portfolio.getMember().getId(),
                portfolio.getPortTitle(),
                portfolio.getPortContent(),
                portfolio.getPortImageUrl(),
                portfolio.getCreatedAt(),
                linkResponses
        );
    }
}
