package com.example.airline.controller;

import com.example.airline.entity.Booking;
import com.example.airline.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAllBookings();
    }

    @GetMapping("/{bookingNumber}")
    public ResponseEntity<Booking> getBooking(@PathVariable String bookingNumber) {
        Booking booking = bookingService.findBookingByNumber(bookingNumber);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.insertBooking(booking);
    }

    @DeleteMapping("/{bookingNumber}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String bookingNumber) {
        boolean deleted = bookingService.deleteBookingByNumber(bookingNumber);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerNumber}")
    public List<Booking> getBookingsByCustomer(@PathVariable String customerNumber) {
        return bookingService.findAllBookingsByCustomerNumber(customerNumber);
    }

    @GetMapping("/flight/{flightNumber}")
    public List<Booking> getBookingsByFlight(@PathVariable String flightNumber) {
        return bookingService.findAllBookingsByFlightNumber(flightNumber);
    }
}