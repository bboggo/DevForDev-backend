package com.backend.devfordev.service.ProjectService;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.converter.ProjectConverter;
import com.backend.devfordev.domain.MemberEntity.Member;
import com.backend.devfordev.domain.ProjectEntity.Project;
import com.backend.devfordev.domain.ProjectEntity.ProjectLink;
import com.backend.devfordev.dto.ProjectDto.ProjectRequest;
import com.backend.devfordev.dto.ProjectDto.ProjectResponse;
import com.backend.devfordev.repository.MemberRepository.MemberRepository;
import com.backend.devfordev.repository.ProjectRepository.ProjectLinkRepository;
import com.backend.devfordev.repository.ProjectRepository.ProjectRepository;
import com.backend.devfordev.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService{
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectLinkRepository projectLinkRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public ProjectResponse.ProjectCreateResponse createProject(ProjectRequest.ProjectCreateRequest request, Long userId, MultipartFile portImage) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_MEMBER));

        String imageUrl;
        try {
            // 이미지 파일이 비어 있는지 확인
            if (portImage == null || portImage.isEmpty()) {
                // 기본 이미지 URL을 설정
                imageUrl = s3Service.saveDefaultProfileImage();
            } else {
                // 이미지 업로드 후 URL 반환
                imageUrl = s3Service.saveProfileImage(portImage);
            }
        } catch (IOException e) {
            throw new MemberHandler(ErrorStatus.IMAGE_UPLOAD_FAILED);
        }

        // 포트폴리오 생성
        Project project = ProjectConverter.toProject(request, member, imageUrl);
        projectRepository.save(project);

        // 링크 리스트 순서 자동 설정 후 변환 및 저장
        List<ProjectLink> links = ProjectConverter.toProjectLinks(request.getLinks(), project);
        for (int i = 0; i < links.size(); i++) {
            links.get(i).setOrderIndex(i + 1); // 자동 순서 설정
        }
        projectLinkRepository.saveAll(links);

        // 포트폴리오 응답 변환
        return ProjectConverter.toProjectResponse(project, links);
    }
}
