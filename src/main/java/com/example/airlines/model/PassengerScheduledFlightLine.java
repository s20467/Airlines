package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class PassengerScheduledFlightLine extends ScheduledFlightLine {

    @Getter @Setter private Double costInDollars;

    @OneToMany(mappedBy = "flightLine", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<PassengerFlight> flights = new HashSet<>();

    public PassengerFlight[] getFlights() {
        return flights.toArray(PassengerFlight[]::new);
    }

    public void addFlight(PassengerFlight flight) {
        if(flight == null || flights.contains(flight))
            return;
        flights.add(flight);
        flight.setFlightLine(this);
    }

    public void removeFlight(PassengerFlight flight) {
        if(flight == null || !flights.contains(flight))
            return;
        flights.remove(flight);
        flight.setFlightLine(null);
    }
}
