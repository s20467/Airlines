package com.example.airlines.repository;

import com.example.airlines.model.PassengerFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PassengerFlightRepository extends JpaRepository<PassengerFlight, Integer>, JpaSpecificationExecutor<PassengerFlight> {
}
