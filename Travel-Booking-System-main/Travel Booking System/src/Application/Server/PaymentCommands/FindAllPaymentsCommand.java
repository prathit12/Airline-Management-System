package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class FindAllPaymentsCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public FindAllPaymentsCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    public Packet execute(Object data){
        try{
            Gson gson = new Gson();
            return new Packet(gson.toJson(paymentDao.findAllPayments()));
        }catch (DaoException e){
            return new Packet(MenuOptions.ErrorOption.DAO_EXCEPTION, e, e.getMessage(), null);
        }
    }
}
