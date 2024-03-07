package Application.Server.BookingCommands;

import Application.DAOs.BookingDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class FindAllBookingsCommand implements Command{
    private BookingDaoInterface bookingDao;

    public FindAllBookingsCommand(BookingDaoInterface bookingDao) {
        this.bookingDao = bookingDao;
    }

    public Packet execute(Object data) {
        try {
            Gson gson = new Gson();
            return new Packet(gson.toJson(bookingDao.findAllBookings()));
        } catch (DaoException e) {
            return new Packet(MenuOptions.ErrorOption.DAO_EXCEPTION, e, e.getMessage(), null);
        }
    }
}
