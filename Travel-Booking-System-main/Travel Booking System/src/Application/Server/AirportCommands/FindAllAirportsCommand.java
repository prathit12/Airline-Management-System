package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class FindAllAirportsCommand implements Command {
    private AirportDaoInterface airportDao;

    public FindAllAirportsCommand(AirportDaoInterface airportDao) {
        this.airportDao = airportDao;
    }

    public Packet execute(Object data) {
        try {
            Gson gson = new Gson();
            return new Packet(gson.toJson(airportDao.findAllAirports()));
        } catch (DaoException e) {
            return new Packet(MenuOptions.ErrorOption.DAO_EXCEPTION, e, e.getMessage(), null);
        }
    }
}