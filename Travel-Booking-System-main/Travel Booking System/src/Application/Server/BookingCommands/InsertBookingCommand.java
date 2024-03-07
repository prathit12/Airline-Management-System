package Application.Server.BookingCommands;

import Application.DAOs.BookingDaoInterface;
import Application.DTOs.Booking;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class InsertBookingCommand implements Command{
    private BookingDaoInterface bookingDao;

    public InsertBookingCommand(BookingDaoInterface bookingDao) {
        this.bookingDao = bookingDao;
    }

    public Packet execute(Object data) {
        Booking booking = (Booking) data;
        try {
            Booking b = bookingDao.insertBooking(booking);
            Gson gson = new Gson();
            return new Packet(gson.toJson(b));
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}