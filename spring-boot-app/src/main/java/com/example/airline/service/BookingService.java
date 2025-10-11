package com.example.airline.service;

import com.example.airline.entity.Booking;
import com.example.airline.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking findBookingByNumber(String bookingNumber) {
        return bookingRepository.findByBookingNumber(bookingNumber).orElse(null);
    }

    public Booking insertBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public boolean deleteBookingByNumber(String bookingNumber) {
        if (bookingRepository.existsByBookingNumber(bookingNumber)) {
            bookingRepository.deleteByBookingNumber(bookingNumber);
            return true;
        }
        return false;
    }

    public List<Booking> findAllBookingsByCustomerNumber(String customerNumber) {
        return bookingRepository.findByCustomerNumber(customerNumber);
    }

    public List<Booking> findAllBookingsByFlightNumber(String flightNumber) {
        return bookingRepository.findByFlightNumber(flightNumber);
    }
}