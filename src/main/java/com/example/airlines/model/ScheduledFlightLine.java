package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class ScheduledFlightLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "SCHEDULED_FLIGHT_LINE_DAY_OF_WEEK",
            joinColumns = @JoinColumn(name = "SCHEDULED_FLIGHT_LINE_ID")
    )
    private Set<DayOfWeek> flightDays;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Airport airportFrom;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Airport airportTo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Airplane airplane;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Pilot primaryPilot;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Pilot secondaryPilot;
}
