package com.example.airline.repository;

import com.example.airline.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.Set;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
    Optional<Payment> findByPaymentNumber(String paymentNumber);
    
    boolean existsByPaymentNumber(String paymentNumber);
    
    void deleteByPaymentNumber(String paymentNumber);
    
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingNumber = :bookingNumber")
    List<Payment> findByBookingNumber(@Param("bookingNumber") String bookingNumber);
    
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    List<Payment> findByBookingId(@Param("bookingId") Integer bookingId);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentMethod = :paymentMethod")
    List<Payment> findByPaymentMethod(@Param("paymentMethod") String paymentMethod);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = :paymentStatus")
    List<Payment> findByPaymentStatus(@Param("paymentStatus") String paymentStatus);
    
    @Query("SELECT DISTINCT p.paymentMethod FROM Payment p")
    Set<String> findDistinctPaymentMethods();
    
    @Query("SELECT p FROM Payment p WHERE p.transactionId = :transactionId")
    Optional<Payment> findByTransactionId(@Param("transactionId") String transactionId);
}