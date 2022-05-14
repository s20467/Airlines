package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pilot extends Employee {

    private LocalDate certificationObtainingDate;

    @OneToMany(mappedBy = "primaryPilot", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> scheduledFlightLinesAsPrimary;

    @OneToMany(mappedBy = "secondaryPilot", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> scheduledFlightLinesAsSecondary;

}
