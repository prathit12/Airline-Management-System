package Application.Server.CustomerCommands;

import Application.DAOs.CustomerDaoInterface;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.TreeSet;

public class GetCustomerNumbersCacheCommand implements Command {
    private CustomerDaoInterface customerDao;

    public GetCustomerNumbersCacheCommand(CustomerDaoInterface customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Packet execute(Object data) {
        try {
            TreeSet<String> customerNumbers = customerDao.populateCustomerCache();
            Gson gson = new Gson();
            return new Packet(gson.toJson(customerNumbers));
        } catch (Exception e) {
            return new Packet(e);
        }
    }
}