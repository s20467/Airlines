package com.example.airlines.repository;

import com.example.airlines.model.PassengerFlight;
import com.example.airlines.model.PassengerFlightBooking;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PassengerFlightBookingRepository extends JpaRepository<PassengerFlightBooking, Integer>, JpaSpecificationExecutor<PassengerFlightBooking> {
}
