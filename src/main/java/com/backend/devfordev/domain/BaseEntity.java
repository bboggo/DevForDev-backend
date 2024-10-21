package com.backend.devfordev.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// DB에 엔티티 저장하기 전에 특정 행동
@EntityListeners(AuditingEntityListener.class)
@Getter
// 객체의 입장에서 공통 매핑정보가 필요할 때 사용
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

    // 삭제
    public void deleteSoftly() {
        this.deletedAt = LocalDateTime.now();
    }

    public void modify() {
        this.modifiedAt = LocalDateTime.now();
    }

    // 확인
    public boolean isSoftDeleted() {
        return null != deletedAt;
    }
}
