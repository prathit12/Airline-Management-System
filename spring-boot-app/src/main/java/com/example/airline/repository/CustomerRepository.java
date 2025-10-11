package com.example.airline.repository;

import com.example.airline.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Optional<Customer> findByCustomerNumber(String customerNumber);
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByCustomerNumber(String customerNumber);
    
    boolean existsByEmail(String email);
    
    void deleteByCustomerNumber(String customerNumber);
    
    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByCustomerNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT c FROM Customer c WHERE c.telNum = :phoneNumber")
    List<Customer> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}