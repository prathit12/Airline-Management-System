package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

public class FindAllFlightsCommand implements Command {
    private FlightDaoInterface flightDao;

    public FindAllFlightsCommand(FlightDaoInterface flightDao) {
        this.flightDao = flightDao;
    }

    public Packet execute(Object data) {
        try {
            Gson gson = new Gson();
            return new Packet(gson.toJson(flightDao.findAllFlights()));
        } catch (DaoException e) {
            return new Packet(MenuOptions.ErrorOption.DAO_EXCEPTION, e, e.getMessage(), null);
        }
    }
}