package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class InsertAirportCommand implements Command {
    private AirportDaoInterface airportDao;

    public InsertAirportCommand(AirportDaoInterface airportDao) {
        this.airportDao = airportDao;
    }

    public Packet execute(Object data) {
        Airport airport = (Airport) data;
        try {
            Airport a = airportDao.insertAirport(airport);
            Gson gson = new Gson();
            // System.out.println("Insert airport: " + a);
            // System.out.println("Insert airport: " + gson.toJson(a));
            return new Packet(gson.toJson(a));
        } catch (DaoException e) {
            // System.out.println("Insert airport error: " + e.getMessage()); // Debugging line
            return new Packet(e);
        }
    }
}