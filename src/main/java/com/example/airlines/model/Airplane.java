package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private String brand;
    @Getter @Setter private String model;
    @Getter @Setter private Integer maxPassengersNumber;
    @Getter @Setter private Integer maxCargoWeightInKg;
    @Getter @Setter private LocalDate lastTechnicalReviewDate;

    @OneToMany(mappedBy = "airplane", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ScheduledFlightLine> flightLines = new HashSet<>();

    public ScheduledFlightLine[] getFlightLines() {
        return flightLines.toArray(ScheduledFlightLine[]::new);
    }

    public void addFlightLine(ScheduledFlightLine flightLine) {
        if(flightLines.contains(flightLine))
            return;
        flightLines.add(flightLine);
        flightLine.setAirplane(this);
    }

    public void removeFlightLine(ScheduledFlightLine flightLine){
        if(!flightLines.contains(flightLine))
            return;
        flightLines.remove(flightLine);
        flightLine.setAirplane(null);
    }
}
