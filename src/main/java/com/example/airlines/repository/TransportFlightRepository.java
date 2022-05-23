package com.example.airlines.repository;

import com.example.airlines.model.TransportFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportFlightRepository extends JpaRepository<TransportFlight, Integer> {
}
