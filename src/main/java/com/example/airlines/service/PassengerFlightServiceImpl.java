package com.example.airlines.service;

import com.example.airlines.model.*;
import com.example.airlines.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PassengerFlightServiceImpl implements PassengerFlightService {

    private final PassengerFlightRepository passengerFlightRepository;
    private final PassengerFlightBookingRepository passengerFlightBookingRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<PassengerFlight> getPassengersFlightAfterNow() {
        return passengerFlightRepository.findAll(CustomSpecifications.departureAfterNow());
    }

    @Override
    public PassengerFlight getById(Integer id) {
        return passengerFlightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger flight with id: " + id + " not found"));
    }


    @Override
    public PassengerFlightBooking createBooking(Integer flightId, Account account) {
        PassengerFlight flight = passengerFlightRepository.findById(flightId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger flight with id: " + flightId + " not found"));
        PassengerFlightBooking newBooking = new PassengerFlightBooking();
        try {
            newBooking.setPassengerClient(clientRepository.getById(account.getClientOwner().getId()));
        } catch (WrongAccountOwnerType exc) {
            System.out.println(exc.getMessage());;
        }
        newBooking.setFlight(flight);
        newBooking.setCreationTime(LocalDateTime.now());
        return passengerFlightBookingRepository.save(newBooking);
    }

    @Override
    public List<PassengerFlightBooking> getBookingsByAccount(Account account) {
        try {
            return passengerFlightBookingRepository.findAll(Specification.where(CustomSpecifications.bookingOwnerId(account.getClientOwner().getId()).and(CustomSpecifications.bookingDepartureAfterNow()).and(CustomSpecifications.bookingStatusEquals(BookingStatus.BOUGHT))));
        } catch (WrongAccountOwnerType exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a client");
        }
    }

    @Override
    public List<PassengerFlightBooking> getBookingsByClientId(Integer clientId) {
        return passengerFlightBookingRepository.findAll(Specification.where(CustomSpecifications.bookingOwnerId(clientId).and(CustomSpecifications.bookingDepartureAfterNow()).and(CustomSpecifications.bookingStatusEquals(BookingStatus.BOUGHT))));
    }

    @Override
    public void cancelBooking(Integer bookingId) {
        PassengerFlightBooking booking = passengerFlightBookingRepository.findById(bookingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        booking.setStatus(BookingStatus.CANCELLED);
        passengerFlightBookingRepository.save(booking);

    }

    @Scheduled(fixedRate = 5 * 60 * 1000L) //every 5min
    void changeFlightStatuses() {
        List<PassengerFlight> flights;
        flights = passengerFlightRepository.findAll(CustomSpecifications.flightsToChangeStatusToInProgress());
        flights.forEach((PassengerFlight flight) -> flight.setStatus(FlightStatus.IN_PROGRESS));
        passengerFlightRepository.saveAll(flights);
        flights = passengerFlightRepository.findAll(CustomSpecifications.flightsToChangeStatusToDone());
        flights.forEach((PassengerFlight flight) -> flight.setStatus(FlightStatus.DONE));
        passengerFlightRepository.saveAll(flights);
    }
}
