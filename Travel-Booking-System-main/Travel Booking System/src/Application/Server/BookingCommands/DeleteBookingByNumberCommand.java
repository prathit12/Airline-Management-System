package Application.Server.BookingCommands;

import Application.DAOs.BookingDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import Application.Server.Command;
import Application.DAOs.PaymentDaoInterface;
import Application.DTOs.Payment;
import java.util.List;
import Application.Protocol.MenuOptions.ErrorOption;
public class DeleteBookingByNumberCommand implements Command{
    private BookingDaoInterface bookingDao;
    private PaymentDaoInterface paymentDao;

    public DeleteBookingByNumberCommand(BookingDaoInterface bookingDao, PaymentDaoInterface paymentDao) {
        this.bookingDao = bookingDao;
        this.paymentDao = paymentDao;
    }

    public Packet execute(Object data) {
        String bookingNumber = (String) data;
        try {
            boolean deleted = bookingDao.deleteBookingByNumber(bookingNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(deleted));
        } catch (DaoException e) {
            String exceptionMessage = e.getMessage() == null ? "Unknown error" : e.getMessage();
            //        System.out.println("DaoException: " + exceptionMessage); // Debugging line
            if (exceptionMessage.contains("foreign key constraint")) {
                try {
                    List<Payment> payments = paymentDao.findAllPaymentsByBookingNumber(bookingNumber);
                    Gson gson = new Gson();
                    String jsonPayments = gson.toJson(payments);
                    Packet packetToSend = new Packet(ErrorOption.DAO_EXCEPTION, e, jsonPayments);
                    //                System.out.println("Server: Sending packet: " + packetToSend.toJson()); // Debugging line
                    return packetToSend; // Updated this line
                } catch (DaoException e1) {
                    //                System.out.println("Inner DaoException: " + e1.getMessage()); // Debugging line
                    return new Packet(e1);
                }
            } else {
                return new Packet(e);
            }
        }
    }
}
