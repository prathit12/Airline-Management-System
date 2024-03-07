package Application.Server.CustomerCommands;

import Application.DAOs.CustomerDaoInterface;
import Application.DTOs.Customer;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

public class CheckDuplicateEmailCommand implements Command{
    private CustomerDaoInterface customerDao;

    public CheckDuplicateEmailCommand(CustomerDaoInterface customerDao) {
        this.customerDao = customerDao;
    }

    public Packet execute(Object data) {
        String email = (String) data;
        try {
            boolean exists = customerDao.checkIfEmailExists(email);
            Gson gson = new Gson();
            return new Packet(gson.toJson(exists));
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}