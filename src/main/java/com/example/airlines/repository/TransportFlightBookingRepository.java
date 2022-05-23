package com.example.airlines.repository;

import com.example.airlines.model.TransportFlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportFlightBookingRepository extends JpaRepository<TransportFlightBooking, Integer> {
}
