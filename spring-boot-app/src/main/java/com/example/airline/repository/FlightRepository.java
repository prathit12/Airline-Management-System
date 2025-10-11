package com.example.airline.repository;

import com.example.airline.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    
    Optional<Flight> findByFlightNumber(String flightNumber);
    
    boolean existsByFlightNumber(String flightNumber);
    
    void deleteByFlightNumber(String flightNumber);
    
    @Query("SELECT f FROM Flight f WHERE f.departureLocation = :location OR f.arrivalLocation = :location")
    List<Flight> findByLocation(@Param("location") String location);
    
    @Query("SELECT f FROM Flight f WHERE f.airport.airportNumber = :airportNumber")
    List<Flight> findByAirportNumber(@Param("airportNumber") String airportNumber);
    
    @Query("SELECT f FROM Flight f WHERE f.airlineName = :airlineName")
    List<Flight> findByAirlineName(@Param("airlineName") String airlineName);
}