package com.example.airlines.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
public class TransportFlightBooking extends FlightBooking {
    @Getter @Setter private String transportedProductName;
    @Getter @Setter private Double transportedProductWeight;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private TransportFlight flight;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private PartnerCompany company;

    public void setFlight(TransportFlight flight) {
        if(this.flight == flight) {
            return;
        }
        else if(flight != null) {
            this.flight = flight;
            flight.addBooking(this);
        }
        else {
            TransportFlight tmpFlight = this.flight;
            this.flight = null;
            tmpFlight.removeBooking(this);
        }
    }

    public void setCompany(PartnerCompany company) {
        if(this.company == company) {
             return;
        }
        else if(company != null) {
            this.company = company;
            company.addBooking(this);
        }
        else {
            PartnerCompany tmpCompany = this.company;
            this.company = null;
            tmpCompany.removeBooking(this);
        }
    }
}
