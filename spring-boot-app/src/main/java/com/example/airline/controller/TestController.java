package com.example.airline.controller;

import com.example.airline.repository.BookingRepository;
import com.example.airline.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @GetMapping("/counts")
    public Map<String, Long> getCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("bookings", bookingRepository.count());
        counts.put("payments", paymentRepository.count());
        return counts;
    }
    
    @GetMapping("/booking-ids")
    public String getBookingIds() {
        try {
            return bookingRepository.findAll().stream()
                .map(b -> b.getBookingId() + ":" + b.getBookingNumber())
                .reduce("", (a, b) -> a + "," + b);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}