package Application.DAOs;

import Application.DTOs.Booking;
import Application.DTOs.Customer;
import Application.DTOs.CustomerBooking;
import Application.DTOs.Flight;
import Application.Exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCustomerBookingDao extends MySqlDao implements CustomerBookingDaoInterface{
    private HelperConnection helperConnection = new HelperConnection();

    @Override
    public List<CustomerBooking> findAllCustomerBookings() throws DaoException {
        List<CustomerBooking> customerBookings = new ArrayList<>();

        try {
            String query = "SELECT c.*, b.*, f.* FROM customer c JOIN booking b ON c.customer_number = b.customer_number JOIN flight f ON b.flight_number = f.flight_number";
            ResultSet resultSet = helperConnection.executeQuery(query);

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customerNumber = resultSet.getString("customer_number");
                String customerName = resultSet.getString("customer_name");
                String customerEmail = resultSet.getString("email");
                String customerPhone = resultSet.getString("tel_num");
                String customerAddress = resultSet.getString("address");

                int bookingId = resultSet.getInt("booking_id");
                String bookingNumber = resultSet.getString("booking_number");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");

                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String airportNumber = resultSet.getString("airport_number");
                String departureLocation = resultSet.getString("departure_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalLocation = resultSet.getString("arrival_location");
                String arrivalTime = resultSet.getString("arrival_time");
                String airlineName = resultSet.getString("airline_name");
                double flightCost = resultSet.getDouble("flight_cost");

                Customer c = new Customer(customerId,customerNumber,customerName,customerEmail,customerPhone,customerAddress);

                Booking b = new Booking(bookingId,bookingNumber,flightNumber,customerNumber,travelDate,seatNumber);

                Flight f = new Flight(flightId,flightNumber,airportNumber,departureLocation,departureTime,arrivalLocation,arrivalTime,airlineName,flightCost);

                // Create a CustomerBooking object and add it to the list
                CustomerBooking customerBooking = new CustomerBooking(c, b, f);
                customerBookings.add(customerBooking);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllCustomerBookings() " + e.getMessage());
        }

        return customerBookings;
    }

}
