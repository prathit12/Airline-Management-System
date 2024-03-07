package Application.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class HelperConnection extends MySqlDao {
    //got the idea for extractimg the repeated code from the following link https://gitbox.apache.org/repos/asf?p=commons-dbcp.git;a=blob_plain;f=doc/BasicDataSourceExample.java;hb=refs/heads/master
    // and https://www.baeldung.com/java-dao-pattern
    //since i noticed that the repeating code in the methods below
    //we can extract the repeated database connection and query execution code into a separate method.
    // This method can then be called by all the methods in the class that need to execute a database query.

    //this is for the read only queries
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(query);

            //thius to set the parameters in the query and also loop through the params array
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            throw e;
        }

        return resultSet;
    }

    //this is for the update queries
    public int executeUpdate(String query, Object... params) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            connection = getConnection();
            ps = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                freeConnection(connection);
            }
        }

        return rowsAffected;
    }

}
