package ca.jrvs.apps.stockquote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private final String url;
    private final Properties properties;


    public  ConnectionManager(String hostname, String databaseName, String username, String password){
        this.url = "jdbc:postgresql://"+ hostname+ "/"+ databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user",username);
        this.properties.setProperty("password" ,password);
        }

        public Connection getConnection(){
        Connection connection;
            try {
                connection =  DriverManager.getConnection(url,properties);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

           return connection;
        }
}
