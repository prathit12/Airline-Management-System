package com.example.airline.service;

import com.example.airline.entity.Payment;
import com.example.airline.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment findPaymentByNumber(String paymentNumber) {
        return paymentRepository.findByPaymentNumber(paymentNumber).orElse(null);
    }

    public Payment insertPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public boolean deletePaymentByNumber(String paymentNumber) {
        if (paymentRepository.existsByPaymentNumber(paymentNumber)) {
            paymentRepository.deleteByPaymentNumber(paymentNumber);
            return true;
        }
        return false;
    }

    public List<Payment> findAllPaymentsByBookingNumber(String bookingNumber) {
        return paymentRepository.findByBookingNumber(bookingNumber);
    }

    public Set<String> uniquePaymentMethod() {
        return paymentRepository.findDistinctPaymentMethods();
    }

    public List<Payment> findPaymentByPaymentMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }
}