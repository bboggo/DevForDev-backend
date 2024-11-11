package com.backend.devfordev.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("LANGUAGE")
public class LanguageAward extends PortfolioAward {
    private String language;
    private String level;
    private String testName;
    private String score;
    private LocalDate acquisitionDate;
}
