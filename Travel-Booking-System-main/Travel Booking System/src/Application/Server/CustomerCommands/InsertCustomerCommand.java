package Application.Server.CustomerCommands;

import Application.DAOs.CustomerDaoInterface;
import Application.DTOs.Customer;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

public class InsertCustomerCommand implements Command {
    private CustomerDaoInterface customerDao;

    public InsertCustomerCommand(CustomerDaoInterface customerDao) {
        this.customerDao = customerDao;
    }

    public Packet execute(Object data) {
        Customer customer = (Customer) data;
        try {
            Customer c = customerDao.insertCustomer(customer);
            Gson gson = new Gson();
//            System.out.println("Insert customer: " + c);
//            System.out.println("Insert customer: " + gson.toJson(c));
            return new Packet(gson.toJson(c));
        } catch (DaoException e) {
//            System.out.println("Insert customer error: " + e.getMessage()); // Debugging line
            return new Packet(e);
        }
    }
}
