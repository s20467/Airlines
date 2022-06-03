package com.example.airlines.repository;

import com.example.airlines.model.BookingStatus;
import com.example.airlines.model.FlightStatus;
import com.example.airlines.model.PassengerFlight;
import com.example.airlines.model.PassengerFlightBooking;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.time.LocalTime;

public class CustomSpecifications {

    public static Specification<PassengerFlight> departureAfterNow() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(root.join("flightLine").get("departureTime"), LocalTime.now()),
                                criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.now())
                        ),
                        criteriaBuilder.greaterThan(root.get("date"), LocalDate.now())
                );
    }

    public static Specification<PassengerFlightBooking> bookingOwnerId(Integer clientId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("passengerClient").get("id"), clientId);
    }

    public static Specification<PassengerFlightBooking> bookingStatusEquals(BookingStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<PassengerFlightBooking> bookingDepartureAfterNow() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(root.join("flight").join("flightLine").get("departureTime"), LocalTime.now()),
                                criteriaBuilder.greaterThanOrEqualTo(root.join("flight").get("date"), LocalDate.now())
                        ),
                        criteriaBuilder.greaterThan(root.join("flight").get("date"), LocalDate.now())
                );
    }

    public static Specification<PassengerFlight> flightsToChangeStatusToInProgress() {
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

    public static Specification<PassengerFlight> flightsToChangeStatusToDone() {
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
}
