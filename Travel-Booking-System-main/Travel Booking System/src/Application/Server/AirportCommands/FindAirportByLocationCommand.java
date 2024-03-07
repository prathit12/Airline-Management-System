package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;

import java.util.List;

public class FindAirportByLocationCommand implements Command {
    private AirportDaoInterface airportDao;

    public FindAirportByLocationCommand(AirportDaoInterface airportDao) {
        this.airportDao = airportDao;
    }

    @Override
    public Packet execute(Object data) {
        String location = (String) data;
        try {
            List<Airport> airports = airportDao.findAirportByLocation(location);
            Gson gson = new Gson();
            String jsonAirports = gson.toJson(airports);
            return new Packet(jsonAirports);
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}
