package Application.Server.CustomerCommands;

import Application.DAOs.CustomerDaoInterface;
import Application.DTOs.Customer;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

public class FindCustomerByNumberCommand implements Command {
    private CustomerDaoInterface customerDao;

    public FindCustomerByNumberCommand(CustomerDaoInterface customerDao) {
        this.customerDao = customerDao;
    }

    public Packet execute(Object data) {
        String customerNumber = (String) data;
        try {
            Customer customer = customerDao.findCustomerByNumber(customerNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(customer));
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}
