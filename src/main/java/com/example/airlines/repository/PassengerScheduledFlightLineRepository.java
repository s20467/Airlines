package com.example.airlines.repository;

import com.example.airlines.model.PassengerScheduledFlightLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerScheduledFlightLineRepository extends JpaRepository<PassengerScheduledFlightLine, Integer> {
}
