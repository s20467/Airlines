package com.example.airlines.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime creationTime;

    private LocalDateTime cancellingTime;

}
