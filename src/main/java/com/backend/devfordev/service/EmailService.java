package com.backend.devfordev.service;

import com.backend.devfordev.apiPayload.code.status.ErrorStatus;
import com.backend.devfordev.apiPayload.exception.handler.MemberHandler;
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
            if (checkDuplication(emailRequest.email())) {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                //String certificationNum = generateCertificationNum();
                Context context = new Context();
                //context.setVariable("certificationNum", certificationNum);
                String message = templateEngine.process("sendEmail", context);

                EmailMessage emailMessage = EmailMessage.builder()
                        .to(emailRequest.email())//보내줘야할 사람
                        //TODO: 여기 제목 채우기
                        .subject("[DevForDev] 비밀번호 찾기")
                        .message(message)
                        .build();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
                mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
                mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
                mimeMessageHelper.setText(emailMessage.getMessage(), true); // 메일 본문 내용, HTML 여부
                emailSender.send(mimeMessage);
                return new EmailResponse(emailRequest.email());
            }
            else {
                throw new MemberHandler(ErrorStatus.DUPLICATED_EMAIL);
            }

        } catch (MessagingException e) {
            // TODO : 고쳐야
            throw new MemberHandler(ErrorStatus.DUPLICATED_EMAIL);
        }
    }

    public boolean checkDuplication(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberHandler(ErrorStatus.DUPLICATED_EMAIL);
        }
        return true;
    }

}
