package Application.DTOs;

import java.math.BigDecimal;

public class Booking {
    private String booking_number;
    private int booking_id;
    private static int booking_id_counter = 0;
    private int customer_id;
    private int flight_id;
    private String passenger_name;
    private String travel_date;
    private String seat_number;
    private BigDecimal total_amount;
    private String booking_status;

    public Booking() {
    }

    public Booking(String booking_number, int customer_id, int flight_id, String passenger_name, String travel_date, String seat_number, BigDecimal total_amount) {
        this.booking_number = booking_number;
        this.booking_id = ++booking_id_counter;
        this.customer_id = customer_id;
        this.flight_id = flight_id;
        this.passenger_name = passenger_name;
        this.travel_date = travel_date;
        this.seat_number = seat_number;
        this.total_amount = total_amount;
        this.booking_status = "CONFIRMED";
    }

    public Booking(int booking_id, String booking_number, int customer_id, int flight_id, String passenger_name, String travel_date, String seat_number, BigDecimal total_amount, String booking_status) {
        this.booking_number = booking_number;
        this.booking_id = booking_id;
        this.customer_id = customer_id;
        this.flight_id = flight_id;
        this.passenger_name = passenger_name;
        this.travel_date = travel_date;
        this.seat_number = seat_number;
        this.total_amount = total_amount;
        this.booking_status = booking_status;
    }

    public String getBooking_number() {
        return booking_number;
    }

    public void setBooking_number(String booking_number) {
        this.booking_number = booking_number;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "booking_number='" + booking_number + '\'' +
                ", booking_id=" + booking_id +
                ", customer_id=" + customer_id +
                ", flight_id=" + flight_id +
                ", passenger_name='" + passenger_name + '\'' +
                ", travel_date='" + travel_date + '\'' +
                ", seat_number='" + seat_number + '\'' +
                ", total_amount=" + total_amount +
                ", booking_status='" + booking_status + '\'' +
                '}';
    }
}