package com.example.airlines.controller;

import com.example.airlines.model.Account;
import com.example.airlines.model.PassengerFlight;
import com.example.airlines.service.ClientService;
import com.example.airlines.service.PassengerFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.PermitAll;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PassengerFlightService passengerFlightService;
    private final ClientService clientService;

    @PermitAll
    @RequestMapping({"passenger-flights", ""})
    public String getPassengerFlights(Model model) {
        List<PassengerFlight> flightList = passengerFlightService.getPassengersFlightAfterNow();
        model.addAttribute("flights", flightList);
        return "passenger_flights";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    @GetMapping("passenger-flights/{id}/buy")
    public String passengerFlightBookingPurchase(@PathVariable Integer id, Model model) {
        model.addAttribute("flight", passengerFlightService.getById(id));
        return "booking_purchase";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    @PostMapping("passenger-flights/{id}/buy")
    public String processPassengerFlightBookingPurchase(@PathVariable Integer id) {
        Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        passengerFlightService.createBooking(id, account);
        return "booking_purchase_success";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    @GetMapping("passenger-flights/my-bookings")
    public String getMyBookings(Model model) {
        Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("bookings", passengerFlightService.getBookingsByAccount(account));
        return "passenger_bookings";
    }

    @PreAuthorize("hasAuthority('ADMIN') or " +
            "hasAuthority('CLIENT') and @customAuthenticationManager.userMatchesBasedOnBookingId(authentication, #bookingId)")
    @PostMapping("/passenger-flights/bookings/{bookingId}/cancel")
    public String cancelBooking(@PathVariable Integer bookingId, Model model) {
        passengerFlightService.cancelBooking(bookingId);
        model.addAttribute("clientId", passengerFlightService.getClientIdByBookingId(bookingId));
        return "booking_cancelled_success";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("clients")
    public String getUsers(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("clients/{clientId}/bookings")
    public String getUsersBookings(@PathVariable Integer clientId, Model model) {
        model.addAttribute("client", clientService.getById(clientId));
        model.addAttribute("bookings", passengerFlightService.getBookingsByClientId(clientId));
        return "passenger_bookings";
    }
}