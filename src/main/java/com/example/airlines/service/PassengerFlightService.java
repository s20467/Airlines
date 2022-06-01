package com.example.airlines.service;

import com.example.airlines.model.Account;
import com.example.airlines.model.PassengerFlight;
import com.example.airlines.model.PassengerFlightBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PassengerFlightService {

    @Transactional
    public List<PassengerFlight> getPassengersFlightAfterNow();
    PassengerFlight getById(Integer id);

    PassengerFlightBooking createBooking(Integer flightId, Account account);

    List<PassengerFlightBooking> getBookingsByAccount(Account account);

    void cancelBooking(Integer bookingId);
}
