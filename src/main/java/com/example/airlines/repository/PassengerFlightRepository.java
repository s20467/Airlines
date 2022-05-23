package com.example.airlines.repository;

import com.example.airlines.model.PassengerFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerFlightRepository extends JpaRepository<PassengerFlight, Integer> {
}
