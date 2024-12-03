package ca.jrvs.apps.jdbcPractice.DAO;

import ca.jrvs.apps.jdbcPractice.DTO.Customer;
import ca.jrvs.apps.jdbcPractice.util.DataAccessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {
private static final String INSERT = "INSERT INTO customer (first_name, last_name," +
        "email, phone, address, city, state, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

private static final String SELECT = "SELECT * FROM CUSTOMER WHERE customer_id = ?";
    private static final Logger log = LoggerFactory.getLogger(CustomerDAO.class);

    private static final String UPDATE = "UPDATE CUSTOMER SET FIRST_NAME = ? , LAST_NAME = ?, EMAIL=?, PHONE =?,ADDRESS=?, CITY = ?,STATE=?,ZIPCODE=? WHERE CUSTOMER_ID = ?";

    private static final String DELETE = "DELETE FROM CUSTOMER WHERE customer_id = ?";

    public CustomerDAO(Connection connection) {
        super(connection);

    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
            customer.setId(id);
            try(PreparedStatement statement = this.connection.prepareStatement(SELECT)) {

               statement.setLong(1,id);
               ResultSet result = statement.executeQuery();

               while(result.next()) {
                   customer.setFirstName( result.getString("first_name"));
                   customer.setLastName(result.getString("last_name"));
                  customer.setEmail(result.getString("email"));
                   customer.setPhone(result.getString("phone"));
                   customer.setAddress(result.getString("address"));
                   customer.setCity(result.getString("city"));
                   customer.setState(result.getString("state"));
                   customer.setZipCode(result.getString("zipcode"));

               }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Customer update(Customer dto) {
        System.out.println(dto);
        try(PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, dto.getFirstName());
            ps.setString(2, dto.getLastName());
            ps.setString(3, dto.getEmail());
            ps.setString(4, dto.getPhone());
            ps.setString(5, dto.getAddress());
            ps.setString(6, dto.getCity());
            ps.setString(7, dto.getState());
            ps.setString(8, dto.getZipCode());
            ps.setLong(9,dto.getId());
            System.out.println(ps.toString());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
            }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, dto.getFirstName());
            ps.setString(2, dto.getLastName());
            ps.setString(3, dto.getEmail());
            ps.setString(4, dto.getPhone());
            ps.setString(5, dto.getAddress());
            ps.setString(6, dto.getCity());
            ps.setString(7, dto.getState());
            ps.setString(8, dto.getZipCode());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
