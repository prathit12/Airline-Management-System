package Application.Server.CustomerCommands;

import Application.DAOs.CustomerDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import Application.Server.Command;
import Application.DAOs.BookingDaoInterface;
import Application.DTOs.Booking;
import java.util.List;
import Application.Protocol.MenuOptions.ErrorOption;



public class DeleteCustomerByNumberCommand implements Command{
    private CustomerDaoInterface customerDao;
    private BookingDaoInterface bookingDao;

    public DeleteCustomerByNumberCommand(CustomerDaoInterface customerDao, BookingDaoInterface bookingDao) {
        this.customerDao = customerDao;
        this.bookingDao = bookingDao;
    }
public Packet execute(Object data) {
    String customerNumber = (String) data;
    try {
        boolean deleted = customerDao.deleteCustomerByNumber(customerNumber);
        Gson gson = new Gson();
        return new Packet(gson.toJson(deleted));
    } catch (DaoException e) {
        String exceptionMessage = e.getMessage() == null ? "Unknown error" : e.getMessage();
//        System.out.println("DaoException: " + exceptionMessage); // Debugging line
        if (exceptionMessage.contains("foreign key constraint")) {
            try {
                List<Booking> bookings = bookingDao.findAllBookingsByCustomerNumber(customerNumber);
                Gson gson = new Gson();
                String jsonBookings = gson.toJson(bookings);
                Packet packetToSend = new Packet(ErrorOption.DAO_EXCEPTION, e, jsonBookings);
//                System.out.println("Server: Sending packet: " + packetToSend.toJson()); // Debugging line
                return packetToSend; // Updated this line
            } catch (DaoException e1) {
//                System.out.println("Inner DaoException: " + e1.getMessage()); // Debugging line
                return new Packet(e1);
            }
        } else {
            return new Packet(e);
        }
    }
}
}
