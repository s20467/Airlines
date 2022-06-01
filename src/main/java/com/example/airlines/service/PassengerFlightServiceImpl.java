package com.example.airlines.service;

import com.example.airlines.model.*;
import com.example.airlines.repository.AccountRepository;
import com.example.airlines.repository.ClientRepository;
import com.example.airlines.repository.PassengerFlightBookingRepository;
import com.example.airlines.repository.PassengerFlightRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PassengerFlightServiceImpl implements PassengerFlightService {

    private final PassengerFlightRepository passengerFlightRepository;
    private final PassengerFlightBookingRepository passengerFlightBookingRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<PassengerFlight> getPassengersFlightAfterNow() {
        return passengerFlightRepository.findAll(departureAfterNow());
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
            return passengerFlightBookingRepository.findAll(Specification.where(bookingOwnerId(account.getClientOwner().getId()).and(bookingDepartureAfterNow()).and(bookingStatusEquals(BookingStatus.BOUGHT))));
        } catch (WrongAccountOwnerType exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a client");
        }
    }

    @Override
    public void cancelBooking(Integer bookingId) {
        PassengerFlightBooking booking = passengerFlightBookingRepository.findById(bookingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        booking.setStatus(BookingStatus.CANCELLED);
        passengerFlightBookingRepository.save(booking);

    }

    private static Specification<PassengerFlight> departureAfterNow() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.join("flightLine").get("departureTime"), LocalTime.now()),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.now())
                ),
                criteriaBuilder.greaterThan(root.get("date"), LocalDate.now())
            );
    }

    private static Specification<PassengerFlightBooking> bookingOwnerId(Integer clientId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("passengerClient").get("id"), clientId);
    }

    private static Specification<PassengerFlightBooking> bookingStatusEquals(BookingStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<PassengerFlightBooking> bookingDepartureAfterNow() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.join("flight").join("flightLine").get("departureTime"), LocalTime.now()),
                    criteriaBuilder.greaterThanOrEqualTo(root.join("flight").get("date"), LocalDate.now())
                ),
                criteriaBuilder.greaterThan(root.join("flight").get("date"), LocalDate.now())
            );
    }

    private static Specification<PassengerFlight> flightsToChangeStatusToInProgress() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("bookings", JoinType.LEFT);
            return
                    criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.and(
                                    criteriaBuilder.lessThanOrEqualTo(root.join("flightLine").get("departureTime"), LocalTime.now()),
                                    criteriaBuilder.lessThanOrEqualTo(root.get("date"), LocalDate.now())
                                ),
                                criteriaBuilder.lessThan(root.get("date"), LocalDate.now())
                        ),
                        criteriaBuilder.equal(root.get("status"), FlightStatus.PLANNED)
                    );
        };
    }

    private static Specification<PassengerFlight> flightsToChangeStatusToDone() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("bookings", JoinType.LEFT);
            return
                    criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.and(
                                    criteriaBuilder.lessThanOrEqualTo(root.join("flightLine").get("arrivalTime"), LocalTime.now()),
                                    criteriaBuilder.lessThanOrEqualTo(root.get("date"), LocalDate.now())
                                ),
                                criteriaBuilder.lessThan(root.get("date"), LocalDate.now())
                    ),
                    criteriaBuilder.equal(root.get("status"), FlightStatus.IN_PROGRESS)
            );
        };
    }

    @Scheduled(fixedRate = 5 * 60 * 1000L) //every 5min
    void changeFlightStatuses() {
        List<PassengerFlight> flights;
        flights = passengerFlightRepository.findAll(flightsToChangeStatusToInProgress());
        flights.forEach((PassengerFlight flight) -> flight.setStatus(FlightStatus.IN_PROGRESS));
        passengerFlightRepository.saveAll(flights);
        flights = passengerFlightRepository.findAll(flightsToChangeStatusToDone());
        flights.forEach((PassengerFlight flight) -> flight.setStatus(FlightStatus.DONE));
        passengerFlightRepository.saveAll(flights);
    }
}
