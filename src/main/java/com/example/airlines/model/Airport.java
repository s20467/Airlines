package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Embedded
    @Getter @Setter private Address address;

    @OneToMany(mappedBy = "airportFrom", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> linesDepartingHere;

    @OneToMany(mappedBy = "airportTo", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> linesArrivingHere;

    public ScheduledFlightLine[] getLinesDepartingHere() {
        return linesDepartingHere.toArray(ScheduledFlightLine[]::new);
    }

    public void addLineDepartingHere(ScheduledFlightLine flightLine) {
        if(flightLine == null || linesDepartingHere.contains(flightLine))
            return;
        linesDepartingHere.add(flightLine);
        flightLine.setAirportFrom(this);
    }

    public void removeLineDepartingHere(ScheduledFlightLine flightLine) {
        if(flightLine == null || !linesDepartingHere.contains(flightLine))
            return;
        linesDepartingHere.remove(flightLine);
        flightLine.setAirportFrom(null);
    }

    public ScheduledFlightLine[] getLinesArrivingHere() {
        return linesArrivingHere.toArray(ScheduledFlightLine[]::new);
    }

    public void addLineArrivingHere(ScheduledFlightLine flightLine) {
        if(flightLine == null || linesArrivingHere.contains(flightLine))
            return;
        linesArrivingHere.add(flightLine);
        flightLine.setAirportTo(this);
    }

    public void removeLineArrivingHere(ScheduledFlightLine flightLine) {
        if(flightLine == null || !linesArrivingHere.contains(flightLine))
            return;
        linesArrivingHere.remove(flightLine);
        flightLine.setAirportTo(this);
    }
}
