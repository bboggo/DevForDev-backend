package com.backend.devfordev.domain;

import com.backend.devfordev.domain.enums.AwardType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("CERTIFICATION")
public class CertificationAward extends PortfolioAward {
    private String certificationName;
    private String issuingInstitution;
    private Integer passingYear;


    protected CertificationAward() {
        super();
    }
    public CertificationAward(Long id, Integer orderIndex, AwardType awardType, Portfolio portfolio,
                              String certificateName, String issuer, Integer passingYear) {
        super(id, orderIndex, awardType, portfolio);
        this.certificationName = certificateName;
        this.issuingInstitution = issuer;
        this.passingYear = passingYear;
    }
}
