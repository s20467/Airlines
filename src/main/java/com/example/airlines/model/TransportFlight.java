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
public class TransportFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Getter private FlightStatus status = FlightStatus.PLANNED;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private TransportScheduledFlightLine flightLine;

    @OneToMany(mappedBy = "flight", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<TransportFlightBooking> bookings = new HashSet<>();

    public void setStatus(FlightStatus status) {
        if(status == FlightStatus.CANCELLED){
            bookings.forEach((TransportFlightBooking booking) -> {
                booking.setStatus(BookingStatus.CANCELLED);
            });
        }
        else if(status == FlightStatus.DONE){
            bookings.forEach((TransportFlightBooking booking) -> {
                booking.setStatus(BookingStatus.REALIZED);
            });
        }
    }

    public void setFlightLine(TransportScheduledFlightLine flightLine) {
        if(this.flightLine == flightLine) {
            return;
        }
        else if(flightLine != null) {
            this.flightLine = flightLine;
            flightLine.addFlight(this);
        }
        else {
            TransportScheduledFlightLine tmpFlightLine = this.flightLine;
            this.flightLine = null;
            tmpFlightLine.removeFlight(this);
        }
    }

    public TransportFlightBooking[] getBookings() {
        return bookings.toArray(TransportFlightBooking[]::new);
    }

    public void addBooking(TransportFlightBooking booking) {
        if(booking == null || bookings.contains(booking))
            return;
        bookings.add(booking);
        booking.setFlight(this);
    }

    public void removeBooking(TransportFlightBooking booking) {
        if(booking == null || !bookings.contains(booking))
            return;
        bookings.remove(booking);
        booking.setFlight(null);
    }
}
