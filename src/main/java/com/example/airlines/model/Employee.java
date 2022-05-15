package com.example.airlines.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Employee extends Person {

    @Getter @Setter private LocalDate hireDate;
    @Getter @Setter private LocalDate dismissalDate;
    @Getter @Setter private Double monthSalary; //todo change double into BigDecimal/Monetary
}
