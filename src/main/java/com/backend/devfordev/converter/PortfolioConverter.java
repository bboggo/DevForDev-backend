package com.backend.devfordev.converter;
import com.backend.devfordev.domain.Member;
import com.backend.devfordev.domain.Portfolio;

import com.backend.devfordev.domain.PortfolioEducation;
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
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<PortfolioEducation> toEducationList(List<PortfolioRequest.PortfolioCreateRequest.EducationRequest> educationRequests, Portfolio portfolio) {
        return educationRequests.stream()
                .map(educationRequest -> PortfolioEducation.builder()
                        .level(educationRequest.getLevel())
                        .institutionName(educationRequest.getInstitutionName())
                        .major(educationRequest.getMajor())
                        .admissionDate(educationRequest.getAdmissionDate())
                        .graduationDate(educationRequest.getGraduationDate())
                        .graduationStatus(educationRequest.getGraduationStatus())
                        .grade(educationRequest.getGrade())
                        .gradeScale(educationRequest.getGradeScale())
                        .orderIndex(educationRequests.indexOf(educationRequest) + 1) // 자동 순서 설정
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());
    }

    public static PortfolioResponse.PortCreateResponse toPortfolioResponse(Portfolio portfolio, List<PortfolioLink> links, List<PortfolioEducation> educations) {
        List<PortfolioResponse.PortCreateResponse.LinkResponse> linkResponses = links.stream()
                .map(link -> new PortfolioResponse.PortCreateResponse.LinkResponse(link.getType(), link.getUrl(), link.getOrderIndex()))
                .collect(Collectors.toList());

        List<PortfolioResponse.PortCreateResponse.EducationResponse> educationResponses = educations.stream()
                .map(education -> new PortfolioResponse.PortCreateResponse.EducationResponse(
                        education.getId(),
                        education.getLevel(),
                        education.getInstitutionName(),
                        education.getMajor(),
                        education.getAdmissionDate(),
                        education.getGraduationDate(),
                        education.getGraduationStatus(),
                        education.getGrade(),
                        education.getGradeScale(),
                        education.getOrderIndex()
                ))
                .collect(Collectors.toList());

        return new PortfolioResponse.PortCreateResponse(
                portfolio.getId(),
                portfolio.getMember().getId(),
                portfolio.getPortTitle(),
                portfolio.getPortContent(),
                portfolio.getPortImageUrl(),
                portfolio.getCreatedAt(),
                linkResponses,
                educationResponses
        );
    }
}
