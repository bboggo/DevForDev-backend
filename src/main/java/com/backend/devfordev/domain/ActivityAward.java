package com.backend.devfordev.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("ACTIVITY")
public class ActivityAward extends PortfolioAward {
    private String activityName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    protected ActivityAward() {
        super();
    }

    public ActivityAward(Long id, Integer orderIndex, String awardType, Portfolio portfolio,
                         String activityName, LocalDate startDate, LocalDate endDate, String description) {
        super(id, orderIndex, awardType, portfolio);
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
