package com.example.airlines.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Employee extends Person {

    private LocalDate hireDate;
    private LocalDate dismissalDate;
    private Double monthSalary; //todo change double into BigDecimal/Monetary
}
