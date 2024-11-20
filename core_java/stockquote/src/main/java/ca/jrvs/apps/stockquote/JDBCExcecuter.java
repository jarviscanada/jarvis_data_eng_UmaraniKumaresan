package ca.jrvs.apps.stockquote;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExcecuter {

    public static void main(String[] args){
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager
                ("localhost","hplussport","postgres","rocky1234");
        Connection connection;
        Statement statement;

        try {
            connection = databaseConnectionManager.getConnection();
             statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
