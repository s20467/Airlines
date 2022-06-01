package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class PassengerFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Getter private FlightStatus status = FlightStatus.PLANNED;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private PassengerScheduledFlightLine flightLine;

    @OneToMany(mappedBy = "flight", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<PassengerFlightBooking> bookings = new HashSet<>();

    public void setStatus(FlightStatus status) {
        if(status == FlightStatus.CANCELLED){
            bookings.forEach((PassengerFlightBooking booking) -> {
                booking.setStatus(BookingStatus.CANCELLED);
            });
        }
        else if(status == FlightStatus.DONE){
            bookings.forEach((PassengerFlightBooking booking) -> {
                booking.setStatus(BookingStatus.REALIZED);
            });
        }
        this.status = status;
    }

    public void setFlightLine(PassengerScheduledFlightLine flightLine) {
        if(this.flightLine == flightLine) {
            return;
        }
        else if(flightLine != null) {
            this.flightLine = flightLine;
            flightLine.addFlight(this);
        }
        else {
            PassengerScheduledFlightLine tmpFlightLine = this.flightLine;
            this.flightLine = null;
            tmpFlightLine.removeFlight(this);
        }
    }

    public PassengerFlightBooking[] getBookings() {
        return bookings.toArray(PassengerFlightBooking[]::new);
    }

    public void addBooking(PassengerFlightBooking booking) {
        if(booking == null || bookings.contains(booking))
            return;
        bookings.add(booking);
        booking.setFlight(this);
    }

    public void removeBooking(PassengerFlightBooking booking) {
        if(booking == null || !bookings.contains(booking))
            return;
        bookings.remove(booking);
        booking.setFlight(null);
    }
}