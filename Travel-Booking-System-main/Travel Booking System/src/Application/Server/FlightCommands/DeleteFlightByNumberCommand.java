package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import Application.Server.Command;
import Application.DAOs.BookingDaoInterface;
import Application.DTOs.Booking;
import java.util.List;
import Application.Protocol.MenuOptions.ErrorOption;
public class DeleteFlightByNumberCommand implements Command{
    private FlightDaoInterface flightDao;
    private BookingDaoInterface bookingDao;
    public DeleteFlightByNumberCommand(FlightDaoInterface flightDao, BookingDaoInterface bookingDao) {
        this.flightDao = flightDao;
        this.bookingDao = bookingDao;
    }
    public Packet execute(Object data) {
        String flightNumber = (String) data;
        try {
            boolean deleted = flightDao.deleteFlightByNumber(flightNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(deleted));
        } catch (DaoException e) {
            String exceptionMessage = e.getMessage() == null ? "Unknown error" : e.getMessage();
            // System.out.println("DaoException: " + exceptionMessage); // Debugging line
            if (exceptionMessage.contains("foreign key constraint")) {
                try {
                    List<Booking> bookings = bookingDao.findAllBookingsByFlightNumber(flightNumber);
                    Gson gson = new Gson();
                    String jsonBookings = gson.toJson(bookings);
                    Packet packetToSend = new Packet(ErrorOption.DAO_EXCEPTION, e, jsonBookings);
                    // System.out.println("Server: Sending packet: " + packetToSend.toJson()); // Debugging line
                    return packetToSend;
                } catch (DaoException e1) {
                    return new Packet(e1);
                }
            } else {
                return new Packet(e);
            }
        }
    }
}