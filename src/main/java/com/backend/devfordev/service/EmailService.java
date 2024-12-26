package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
import com.backend.devfordev.domain.MemberEntity.Member;
import com.backend.devfordev.dto.EmailMessage;
import com.backend.devfordev.dto.EmailRequest;
import com.backend.devfordev.dto.EmailResponse;
import com.backend.devfordev.repository.MemberRepository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final MemberRepository memberRepository;

    public EmailResponse sendMail(EmailRequest emailRequest) {
        try {

                // 사용자 정보를 가져오는 로직 추가
                Member member = memberRepository.findByEmail(emailRequest.email())
                        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

                // MimeMessage 객체 생성
                MimeMessage mimeMessage = emailSender.createMimeMessage();

                // 타임리프 Context에 변수 설정
                Context context = new Context();
                context.setVariable("name", member.getName()); // 사용자 이름 추가
                context.setVariable("email", emailRequest.email()); // 이메일 추가
                String message = templateEngine.process("sendEmail", context); // 타임리프 템플릿 처리

                // 이메일 메시지 빌드
                EmailMessage emailMessage = EmailMessage.builder()
                        .to(emailRequest.email()) // 수신자 이메일
                        .subject("[DevForDev] 비밀번호 찾기")
                        .message(message) // 타임리프로 렌더링된 메시지
                        .build();

                // MimeMessageHelper로 이메일 설정
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
                mimeMessageHelper.setTo(emailMessage.getTo()); // 수신자
                mimeMessageHelper.setSubject(emailMessage.getSubject()); // 제목
                mimeMessageHelper.setText(emailMessage.getMessage(), true); // 본문

                // 이메일 전송
                emailSender.send(mimeMessage);
                return new EmailResponse(emailRequest.email());

        } catch (MessagingException e) {
            throw new MemberHandler(ErrorStatus.EMAIL_SEND_ERROR);
        }
    }

//    public boolean checkDuplication(String email) {
//        if (memberRepository.findByEmail(email).isPresent()) {
//            throw new MemberHandler(ErrorStatus.DUPLICATED_EMAIL);
//        }
//        return true;
//    }

}
