package ca.jrvs.apps.stockquote;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

    public static void main(String[] args) {

    ConnectionManager connectionManager = new ConnectionManager
            ("localhost","hplussport","postgres","rocky1234");
        Connection connection = null;
        try {
             connection = connectionManager.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {

            }
        }
    }

}
