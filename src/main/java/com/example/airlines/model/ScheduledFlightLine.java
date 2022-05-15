package com.example.airlines.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
@NoArgsConstructor
public abstract class ScheduledFlightLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "SCHEDULED_FLIGHT_LINE_DAY_OF_WEEK",
            joinColumns = @JoinColumn(name = "SCHEDULED_FLIGHT_LINE_ID")
    )
    private Set<DayOfWeek> flightDays;
    @Getter @Setter private LocalTime departureTime;
    @Getter @Setter private LocalTime arrivalTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Airport airportFrom;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Airport airportTo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Airplane airplane;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Pilot primaryPilot;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Pilot secondaryPilot;

    public DayOfWeek[] flightDays(){
        return flightDays.toArray(DayOfWeek[]::new);
    }

    public void addFlightDay(DayOfWeek flightDay) {
        if(flightDay == null || flightDays.contains(flightDay))
            return;
        flightDays.add(flightDay);
    }

    public void removeFlightDay(DayOfWeek flightDay) {
        if(flightDay == null || !flightDays.contains(flightDay))
            return;
        flightDays.remove(flightDay);
    }

    public void setAirportFrom(Airport airportFrom) {
        if(this.airportFrom == airportFrom){
            return;
        }
        else if(airportFrom != null) {
            this.airportFrom = airportFrom;
            airportFrom.removeLineDepartingHere(this);
        }
        else {
            Airport tmpAirportFrom = this.airportFrom;
            this.airportFrom = null;
            tmpAirportFrom.removeLineDepartingHere(this);
        }
    }

    public void setAirportTo(Airport airportTo) {
        if(this.airportTo == airportTo) {
            return;
        }
        else if(airportTo != null) {
            this.airportTo = airportTo;
            airportTo.removeLineArrivingHere(this);
        }
        else {
            Airport tmpAirportTo = this.airportTo;
            this.airportTo = null;
            tmpAirportTo.removeLineArrivingHere(this);
        }
    }

    public void setAirplane(Airplane airplane) {
        if(this.airplane == airplane){
            return;
        }
        else if(airplane != null) {
            this.airplane = airplane;
            airplane.addFlightLine(this);
        }
        else {
            Airplane tmpAirplane = this.airplane;
            this.airplane = null;
            tmpAirplane.removeFlightLine(this);
        }
    }

    public void setPrimaryPilot(Pilot primaryPilot) {
        if(this.primaryPilot == primaryPilot) {
            return;
        }
        else if(primaryPilot != null) {
            this.primaryPilot = primaryPilot;
            primaryPilot.addFlightLineAsPrimary(this);
        }
        else {
            Pilot tmpPrimaryPilot = this.primaryPilot;
            this.primaryPilot = null;
            tmpPrimaryPilot.removeFlightLineAsPrimary(this);
        }
    }

    public void setSecondaryPilot(Pilot secondaryPilot) {
        if(this.secondaryPilot == secondaryPilot) {
            return;
        }
        else if(secondaryPilot != null) {
            this.secondaryPilot = secondaryPilot;
            secondaryPilot.addFlightLineAsSecondary(this);
        }
        else {
            Pilot tmpSecondaryPilot = this.secondaryPilot;
            this.secondaryPilot = null;
            tmpSecondaryPilot.removeFlightLineAsSecondary(this);
        }
    }
}
