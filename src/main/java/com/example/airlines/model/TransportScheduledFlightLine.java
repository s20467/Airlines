package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class TransportScheduledFlightLine extends ScheduledFlightLine {

    @Getter @Setter private Double costForKgInDollars; //todo change double into BigDecimal/Monetary

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Account account;
    @OneToMany(mappedBy = "flightLine", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<TransportFlight> flights = new HashSet<>();

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
