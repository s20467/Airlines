package com.example.airlines.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
public class PassengerFlightBooking extends FlightBooking {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private PassengerFlight flight;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @Getter private Client passengerClient;

    public void setFlight(PassengerFlight flight) {
        if(this.flight == flight) {
            return;
        }
        else if(flight != null) {
            this.flight = flight;
            flight.addBooking(this);
        }
        else {
            PassengerFlight tmpFlight = this.flight;
            this.flight = null;
            tmpFlight.removeBooking(this);
        }
    }

    public void setPassengerClient(Client passengerClient) {
        if(this.passengerClient == passengerClient) {
            return;
        }
        else if(passengerClient != null) {
            this.passengerClient = passengerClient;
            passengerClient.addBooking(this);
        }
        else {
            Client tmpPassengerClient = this.passengerClient;
            this.passengerClient = null;
            tmpPassengerClient.removeBooking(this);
        }
    }


}
