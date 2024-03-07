package Application.Server;

import Application.DAOs.MySqlAirportDao;
import Application.DAOs.MySqlBookingDao;
import Application.DAOs.MySqlCustomerDao;
import Application.DAOs.MySqlFlightDao;
import Application.DAOs.MySqlPaymentDao;
import Application.Exceptions.DaoException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args)
    {
        autoInitialize();
        Server server = new Server(8080);
        server.start();
    }

    private static void autoInitialize() {
        MySqlCustomerDao mySqlCustomerDao = new MySqlCustomerDao();
        MySqlAirportDao mySqlAirportDao = new MySqlAirportDao();
        MySqlFlightDao mySqlFlightDao = new MySqlFlightDao();
        MySqlBookingDao mySqlBookingDao = new MySqlBookingDao();
        MySqlPaymentDao mySqlPaymentDao = new MySqlPaymentDao();
        try {
            mySqlCustomerDao.populateCustomerCache();
            mySqlAirportDao.populateAirportCache();
            mySqlFlightDao.populateFlightCache();
            mySqlBookingDao.populateBookingCache();
            mySqlPaymentDao.populatePaymentCache();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private int portNumber;

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server: Server started. Listening for connections on port " + portNumber + "...");

            int clientNumber = 0;

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " connected from " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, clientNumber);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e.getMessage());
        }
    }
}
