package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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
    private Set<TransportFlightBooking> bookings = new HashSet<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Account account;

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

    public void setAccount(Account account) throws WrongAccountOwnerType {
        if(this.account == account) {
            return;
        }
        else if(account != null) {
            account.setCompanyOwner(this);
            this.account = account;
        }
        else {
            Account tmpAccount = this.account;
            this.account = null;
            tmpAccount.resetOwner();
        }
    }
}
