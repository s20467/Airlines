package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PassengerScheduledFlightLine extends ScheduledFlightLine {
    private Double costInDollars;
}
