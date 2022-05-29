package com.example.airlines.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Getter @Setter private BookingStatus status = BookingStatus.BOUGHT;

    @Getter @Setter private LocalDateTime creationTime;

    @Getter @Setter private LocalDateTime cancellingTime;

}
