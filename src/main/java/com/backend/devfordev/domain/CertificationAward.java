package com.backend.devfordev.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CERTIFICATION")
public class CertificationAward extends PortfolioAward {
    private String certificationName;
    private String issuingInstitution;
    private Integer passingYear;
}
