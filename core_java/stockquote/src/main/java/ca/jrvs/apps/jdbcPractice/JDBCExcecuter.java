package ca.jrvs.apps.jdbcPractice;

import ca.jrvs.apps.jdbcPractice.DAO.CustomerDAO;
import ca.jrvs.apps.jdbcPractice.DTO.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExcecuter {

    public static void main(String[] args){
        JDBCExcecuter excecuter = new JDBCExcecuter();
        //excecuter.create();

        excecuter.update();
        excecuter.read();
        excecuter.delete();
        
    }

    public  void read(){
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager
                ("localhost","hplussport","postgres","rocky1234");
        Connection connection = null;
        Statement statement;

        try {
            connection = databaseConnectionManager.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer = customerDAO.findById(1001);
            System.out.println(customer);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        }
    public void create(){
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager
                ("localhost","hplussport","postgres","rocky1234");
        Connection connection;
        Statement statement;

        try {
            connection = databaseConnectionManager.getConnection();
            Customer customer = new Customer();
            customer.setId(1);
            customer.setFirstName("John");
            customer.setLastName("Smith");
            customer.setEmail("john.smith@gmail.com");
            customer.setPhone("78786632");
            customer.setAddress("2520 Eglinton ave");
            customer.setCity("San Francisco");
            customer.setState("CA");
            customer.setZipCode("234");

            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.create(customer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager
                ("localhost","hplussport","postgres","rocky1234");
        Connection connection;
        Statement statement;

        try {
            connection = databaseConnectionManager.getConnection();
            Customer customer = new Customer();
            customer.setFirstName("umarani");
            customer.setLastName("kumr");
            customer.setEmail("john.smith@gmail.com");
            customer.setPhone("78786632");
            customer.setAddress("2520 Eglinton ave");
            customer.setCity("San Francisco");
            customer.setState("CA");
            customer.setZipCode("234");
            customer.setId(1001);

            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.update(customer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(){
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager
                ("localhost","hplussport","postgres","rocky1234");
        Connection connection;
        Statement statement;

        try {
            connection = databaseConnectionManager.getConnection();

           CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.delete(1001);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
