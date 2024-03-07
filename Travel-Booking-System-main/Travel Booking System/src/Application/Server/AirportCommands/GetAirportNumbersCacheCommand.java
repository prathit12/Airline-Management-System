package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.TreeSet;
public class GetAirportNumbersCacheCommand implements Command{
    private AirportDaoInterface airportDao;
    public GetAirportNumbersCacheCommand(AirportDaoInterface airportDao){
        this.airportDao = airportDao;
    }
    @Override
    public Packet execute(Object data){
        try{
            TreeSet<String> airportNumbers = airportDao.populateAirportCache();
            Gson gson = new Gson();
            return new Packet(gson.toJson(airportNumbers));
        }catch(Exception e){
            return new Packet(e);
        }
    }
}
