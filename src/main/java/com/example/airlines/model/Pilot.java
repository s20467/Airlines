package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Pilot extends Employee {

    @Getter @Setter private LocalDate certificationObtainingDate;

    @OneToMany(mappedBy = "primaryPilot", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> flightLinesAsPrimary = new HashSet<>();

    @OneToMany(mappedBy = "secondaryPilot", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> flightLinesAsSecondary = new HashSet<>();

    public void addFlightLineAsPrimary(ScheduledFlightLine flightLine) {
        if(flightLine == null || flightLinesAsPrimary.contains(flightLine))
            return;
        flightLinesAsPrimary.add(flightLine);
        flightLine.setPrimaryPilot(this);
    }

    public void removeFlightLineAsPrimary(ScheduledFlightLine flightLine) {
        if(flightLine == null || !flightLinesAsPrimary.contains(flightLine))
            return;
        flightLinesAsPrimary.remove(flightLine);
        flightLine.setPrimaryPilot(null);
    }

    public void addFlightLineAsSecondary(ScheduledFlightLine flightLine) {
        if(flightLine == null || flightLinesAsSecondary.contains(flightLine))
            return;
        flightLinesAsSecondary.add(flightLine);
        flightLine.setSecondaryPilot(this);
    }

    public void removeFlightLineAsSecondary(ScheduledFlightLine flightLine) {
        if(flightLine == null || !flightLinesAsSecondary.contains(flightLine))
            return;
        flightLinesAsSecondary.remove(flightLine);
        flightLine.setSecondaryPilot(null);
    }

}
