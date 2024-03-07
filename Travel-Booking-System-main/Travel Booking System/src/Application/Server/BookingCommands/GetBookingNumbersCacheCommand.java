package Application.Server.BookingCommands;

import Application.DAOs.BookingDaoInterface;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.TreeSet;
public class GetBookingNumbersCacheCommand implements Command{
    private BookingDaoInterface bookingDao;
    public GetBookingNumbersCacheCommand(BookingDaoInterface bookingDao){
        this.bookingDao = bookingDao;
    }
    @Override
    public Packet execute(Object data){
        try{
            TreeSet<String> bookingNumbers = bookingDao.populateBookingCache();
            Gson gson = new Gson();
            return new Packet(gson.toJson(bookingNumbers));
        }catch(Exception e){
            return new Packet(e);
        }
    }
}
