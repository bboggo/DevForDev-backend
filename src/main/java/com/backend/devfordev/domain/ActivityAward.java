package com.backend.devfordev.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("ACTIVITY")
public class ActivityAward extends PortfolioAward {
    private String activityName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
