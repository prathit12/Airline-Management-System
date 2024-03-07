package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class FindAirportByNumberCommand implements Command {
    private AirportDaoInterface airportDao;

    public FindAirportByNumberCommand(AirportDaoInterface airportDao) {
        this.airportDao = airportDao;
    }

    public Packet execute(Object data) {
        String airportNumber = (String) data;
        try {
            Airport airport = airportDao.findAirportByNumber(airportNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(airport));
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}