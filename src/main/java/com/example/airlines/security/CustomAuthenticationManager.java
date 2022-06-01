package com.example.airlines.security;

import com.example.airlines.model.Account;
import com.example.airlines.model.PassengerFlightBooking;
import com.example.airlines.model.WrongAccountOwnerType;
import com.example.airlines.repository.PassengerFlightBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager {

    private final PassengerFlightBookingRepository passengerFlightBookingRepository;

    public boolean userMatchesBasedOnBookingId(Authentication authentication, int bookingId) throws WrongAccountOwnerType {
        Account account = (Account) authentication.getPrincipal();
        PassengerFlightBooking booking = passengerFlightBookingRepository.getById(bookingId);
        return booking.getPassengerClient().getId().equals(account.getClientOwner().getId());
    }
}
