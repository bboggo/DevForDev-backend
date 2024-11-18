package com.backend.devfordev.converter;
import com.backend.devfordev.domain.*;

import com.backend.devfordev.dto.PortfolioRequest;
import com.backend.devfordev.dto.PortfolioResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PortfolioConverter {

    public static Portfolio toPortfolio(PortfolioRequest.PortfolioCreateRequest request, Member member) {

        Portfolio portfolio = Portfolio.builder()
                .portTitle(request.getPortTitle())
                .portContent(request.getPortContent())
                .portPosition(request.getPortPosition())
                .portImageUrl(request.getPortImageUrl())
                .member(member)
                .build();

        // techStacks 리스트를 쉼표로 구분된 문자열로 변환하여 저장
        portfolio.setTechStacks(Collections.singletonList(String.join(",", request.getTechStacks())));

        return portfolio;
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

    public static List<PortfolioAward> toAwardList(List<PortfolioRequest.PortfolioCreateRequest.AwardRequest> awardRequests, Portfolio portfolio) {
        return awardRequests.stream().map(awardRequest -> {
            switch (awardRequest.getAwardType()) {
                case "COMPETITION":
                    return new CompetitionAward(
                            null,
                            awardRequests.indexOf(awardRequest) + 1,
                            awardRequest.getAwardType(),
                            portfolio,
                            awardRequest.getCompetitionName(),
                            awardRequest.getHostingInstitution(),
                            awardRequest.getCompetitionDate()
                    );
                case "CERTIFICATE":
                    return new CertificationAward(
                            null,
                            awardRequests.indexOf(awardRequest) + 1,
                            awardRequest.getAwardType(),
                            portfolio,
                            awardRequest.getCertificateName(),
                            awardRequest.getIssuer(),
                            awardRequest.getPassingYear()
                    );
                case "LANGUAGE":
                    return new LanguageAward(
                            null,
                            awardRequests.indexOf(awardRequest) + 1,
                            awardRequest.getAwardType(),
                            portfolio,
                            awardRequest.getLanguage(),
                            awardRequest.getLevel(),
                            awardRequest.getTestName(),
                            awardRequest.getScore(),
                            awardRequest.getObtainedDate()
                    );
                case "ACTIVITY":
                    return new ActivityAward(
                            null,
                            awardRequests.indexOf(awardRequest) + 1,
                            awardRequest.getAwardType(),
                            portfolio,
                            awardRequest.getActivityName(),
                            awardRequest.getStartDate(),
                            awardRequest.getEndDate(),
                            awardRequest.getDescription()
                    );
                default:
                    throw new IllegalArgumentException("Invalid award type: " + awardRequest.getAwardType());
            }
        }).collect(Collectors.toList());
    }

    public static PortfolioResponse.PortCreateResponse toPortfolioResponse(Portfolio portfolio, List<PortfolioLink> links, List<PortfolioEducation> educations, List<PortfolioAward> awards) {
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

        List<PortfolioResponse.PortCreateResponse.AwardResponse> awardResponses = awards.stream()
                .map(award -> {
                    if (award instanceof CompetitionAward) {
                        CompetitionAward compAward = (CompetitionAward) award;
                        return new PortfolioResponse.PortCreateResponse.AwardResponse(
                                award.getAwardType(), award.getOrderIndex(),
                                compAward.getCompetitionName(), compAward.getHostingInstitution(),
                                compAward.getCompetitionDate()
                        );
                    } else if (award instanceof CertificationAward) {
                        CertificationAward certAward = (CertificationAward) award;
                        return new PortfolioResponse.PortCreateResponse.AwardResponse(
                                award.getAwardType(), award.getOrderIndex(),
                                certAward.getCertificationName(), certAward.getIssuingInstitution(), certAward.getPassingYear()
                        );
                    } else if (award instanceof LanguageAward) {
                        LanguageAward langAward = (LanguageAward) award;
                        return new PortfolioResponse.PortCreateResponse.AwardResponse(
                                award.getAwardType(), award.getOrderIndex(),
                                langAward.getLanguage(), langAward.getLevel(), langAward.getTestName(),
                                langAward.getScore(), langAward.getAcquisitionDate()
                        );
                    } else if (award instanceof ActivityAward) {
                        ActivityAward actAward = (ActivityAward) award;
                        return new PortfolioResponse.PortCreateResponse.AwardResponse(
                                award.getAwardType(), award.getOrderIndex(),
                                actAward.getActivityName(), actAward.getStartDate(),
                                actAward.getEndDate(), actAward.getDescription()
                        );
                    }
                    return null;
                })
                .collect(Collectors.toList());

        // techStacks 문자열을 리스트로 변환하여 설정
        List<String> techStacks = portfolio.getTechStacks() != null ? portfolio.getTechStacks() : new ArrayList<>();

        return new PortfolioResponse.PortCreateResponse(
                portfolio.getId(),
                portfolio.getMember().getId(),
                portfolio.getPortTitle(),
                portfolio.getPortContent(),
                portfolio.getPortPosition(),
                techStacks,
                portfolio.getPortImageUrl(),
                portfolio.getCreatedAt(),
                linkResponses,
                educationResponses,
                awardResponses
        );
    }
}
