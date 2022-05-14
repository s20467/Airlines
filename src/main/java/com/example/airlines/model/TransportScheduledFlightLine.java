package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportScheduledFlightLine extends ScheduledFlightLine {

    private Double costForKgInDollars; //todo change double into BigDecimal/Monetary
}
