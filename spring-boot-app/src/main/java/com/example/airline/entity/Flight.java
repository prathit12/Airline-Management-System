package com.example.airline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "flight")
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer flightId;
    
    @NotBlank
    @Size(max = 10)
    @Column(name = "flight_number", unique = true, nullable = false)
    private String flightNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_number", referencedColumnName = "airport_number")
    private Airport airport;
    
    @Size(max = 50)
    @Column(name = "departure_location")
    private String departureLocation;
    
    @Column(name = "departure_time")
    private LocalTime departureTime;
    
    @Size(max = 50)
    @Column(name = "arrival_location")
    private String arrivalLocation;
    
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    
    @Size(max = 30)
    @Column(name = "airline_name")
    private String airlineName;
    
    @DecimalMin("0.0")
    @Column(name = "flight_cost", precision = 10, scale = 2)
    private BigDecimal flightCost;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Flight() {}
    
    public Flight(String flightNumber, Airport airport, String departureLocation, 
                  LocalTime departureTime, String arrivalLocation, LocalTime arrivalTime,
                  String airlineName, BigDecimal flightCost) {
        this.flightNumber = flightNumber;
        this.airport = airport;
        this.departureLocation = departureLocation;
        this.departureTime = departureTime;
        this.arrivalLocation = arrivalLocation;
        this.arrivalTime = arrivalTime;
        this.airlineName = airlineName;
        this.flightCost = flightCost;
    }
    
    // Getters and Setters
    public Integer getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public Airport getAirport() {
        return airport;
    }
    
    public void setAirport(Airport airport) {
        this.airport = airport;
    }
    
    public String getDepartureLocation() {
        return departureLocation;
    }
    
    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }
    
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public String getArrivalLocation() {
        return arrivalLocation;
    }
    
    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }
    
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    
    public BigDecimal getFlightCost() {
        return flightCost;
    }
    
    public void setFlightCost(BigDecimal flightCost) {
        this.flightCost = flightCost;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Set<Booking> getBookings() {
        return bookings;
    }
    
    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}