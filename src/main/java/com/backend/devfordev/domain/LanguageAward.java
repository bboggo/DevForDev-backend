package com.backend.devfordev.domain;

import com.backend.devfordev.domain.enums.AwardType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("LANGUAGE")
public class LanguageAward extends PortfolioAward {
    private String language;
    private String level;
    private String testName;
    private String score;
    private LocalDate acquisitionDate;
    protected LanguageAward() {
        super();
    }

    public LanguageAward(Long id, Integer orderIndex, AwardType awardType, Portfolio portfolio,
                         String language, String level, String testName, String score, LocalDate obtainedDate) {
        super(id, orderIndex, awardType, portfolio);
        this.language = language;
        this.level = level;
        this.testName = testName;
        this.score = score;
        this.acquisitionDate = acquisitionDate;
    }
}
