package com.example.airlines.repository;

import com.example.airlines.model.PassengerFlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerFlightBookingRepository extends JpaRepository<PassengerFlightBooking, Integer> {
}
