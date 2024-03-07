package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

public class FindFlightByNumberCommand implements Command{
    private FlightDaoInterface flightDao;

    public FindFlightByNumberCommand(FlightDaoInterface flightDao) {
        this.flightDao = flightDao;
    }

    public Packet execute(Object data) {
        String flightNumber = (String) data;
        try {
            Flight flight = flightDao.findFlightByNumber(flightNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(flight));
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}