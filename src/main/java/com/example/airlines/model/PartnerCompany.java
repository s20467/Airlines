package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class PartnerCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @Getter @Setter private String name;

    @Embedded
    @Getter @Setter private Address address;

    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<TransportFlightBooking> bookings;

    public TransportFlightBooking[] getBookings() {
        return bookings.toArray(TransportFlightBooking[]::new);
    }

    public void addBooking(TransportFlightBooking booking) {
        if(booking == null || bookings.contains(booking))
            return;
        bookings.add(booking);
        booking.setCompany(this);
    }

    public void removeBooking(TransportFlightBooking booking) {
        if(booking == null || !bookings.contains(booking))
            return;
        bookings.remove(booking);
        booking.setCompany(null);
    }
}
