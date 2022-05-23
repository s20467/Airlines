package com.example.airlines.repository;

import com.example.airlines.model.TransportScheduledFlightLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportScheduledFlightLineRepository extends JpaRepository<TransportScheduledFlightLine, Integer> {
}
