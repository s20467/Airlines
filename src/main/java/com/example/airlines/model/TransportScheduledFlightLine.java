package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
public class TransportScheduledFlightLine extends ScheduledFlightLine {

    @Getter @Setter private Double costForKgInDollars; //todo change double into BigDecimal/Monetary

    @OneToMany(mappedBy = "flightLine", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<TransportFlight> flights;

    public TransportFlight[] getFlights() {
        return flights.toArray(TransportFlight[]::new);
    }

    public void addFlight(TransportFlight flight) {
        if(flight == null || flights.contains(flight))
            return;
        flights.add(flight);
        flight.setFlightLine(this);
    }

    public void removeFlight(TransportFlight flight) {
        if(flight == null || !flights.contains(flight))
            return;
        flights.remove(flight);
        flight.setFlightLine(null);
    }
}
