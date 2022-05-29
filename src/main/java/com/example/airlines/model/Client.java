package com.example.airlines.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Client extends Person {


    @OneToMany(mappedBy = "passengerClient", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<PassengerFlightBooking> bookings = new HashSet<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @Getter private Account account;

    public PassengerFlightBooking[] getBookings() {
        return bookings.toArray(PassengerFlightBooking[]::new);
    }

    public void addBooking(PassengerFlightBooking booking) {
        if(booking == null || bookings.contains(booking))
            return;
        bookings.add(booking);
        booking.setPassengerClient(this);
    }

    public void removeBooking(PassengerFlightBooking booking) {
        if(booking == null || !bookings.contains(booking))
            return;
        bookings.remove(booking);
        booking.setPassengerClient(null);
    }

    public void setAccount(Account account) throws WrongAccountOwnerType {
        if(this.account == account) {
            return;
        }
        else if(account != null) {
            account.setClientOwner(this);
            this.account = account;
        }
        else {
            Account tmpAccount = this.account;
            this.account = null;
            tmpAccount.resetOwner();
        }
    }
}
