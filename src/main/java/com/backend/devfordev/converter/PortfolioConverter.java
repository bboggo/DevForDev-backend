package com.backend.devfordev.converter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.Portfolio;

import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;

public class PortfolioConverter {

    public static Portfolio toPortfolio(PortfolioRequest.PortfolioCreateRequest request, Member member) {

        return Portfolio.builder()
                .portTitle(request.getPortTitle())
                .portContent(request.getPortContent())
                .portImageUrl(request.getPortImageUrl())
                .member(member)
                .build();
    }


    public static PortfolioResponse.PortCreateResponse toPortfolioResponse(Portfolio portfolio) {
        return new PortfolioResponse.PortCreateResponse(
                portfolio.getId(),
                portfolio.getMember().getId(),
                portfolio.getPortTitle(),
                portfolio.getPortContent(),
                portfolio.getPortImageUrl(),
                portfolio.getCreatedAt()
        );
    }
}
