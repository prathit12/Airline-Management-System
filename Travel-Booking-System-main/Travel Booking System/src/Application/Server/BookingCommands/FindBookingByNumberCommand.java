package Application.Server.BookingCommands;

import Application.DAOs.BookingDaoInterface;
import Application.DTOs.Booking;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class FindBookingByNumberCommand implements Command{
    private BookingDaoInterface bookingDao;
    public FindBookingByNumberCommand(BookingDaoInterface bookingDao){
        this.bookingDao = bookingDao;
    }
    public Packet execute(Object data){
        String bookingNumber = (String) data;
        try{
            Booking booking = bookingDao.findBookingByNumber(bookingNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(booking));
        }catch (DaoException e){
            return new Packet(e);
        }
    }
}