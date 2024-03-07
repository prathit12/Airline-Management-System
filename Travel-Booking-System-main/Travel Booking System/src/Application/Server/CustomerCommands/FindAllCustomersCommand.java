package Application.Server.CustomerCommands;

import Application.DAOs.CustomerDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

public class FindAllCustomersCommand implements Command {
    private CustomerDaoInterface customerDao;

    public FindAllCustomersCommand(CustomerDaoInterface customerDao) {
        this.customerDao = customerDao;
    }

    public Packet execute(Object data) {
        try {
            Gson gson = new Gson();
            return new Packet(gson.toJson(customerDao.findAllCustomers()));
        } catch (DaoException e) {
            return new Packet(MenuOptions.ErrorOption.DAO_EXCEPTION, e, e.getMessage(), null);
        }
    }
}
