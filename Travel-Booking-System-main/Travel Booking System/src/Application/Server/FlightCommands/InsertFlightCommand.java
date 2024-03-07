package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class InsertFlightCommand implements Command{
    private FlightDaoInterface flightDao;

    public InsertFlightCommand(FlightDaoInterface flightDao) {
        this.flightDao = flightDao;
    }

    public Packet execute(Object data) {
        Flight flight = (Flight) data;
        try {
            Flight f = flightDao.insertFlight(flight);
            Gson gson = new Gson();
            // System.out.println("Insert flight: " + f);
            // System.out.println("Insert flight: " + gson.toJson(f));
            return new Packet(gson.toJson(f));
        } catch (DaoException e) {
            // System.out.println("Insert flight error: " + e.getMessage()); // Debugging line
            return new Packet(e);
        }
    }
}
