package ca.jrvs.apps.stockquote.DAO;

import ca.jrvs.apps.stockquote.DTO.Position;
import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {
    private Connection connection;

    private static final String INSERT =
            "INSERT INTO POSITION (SYMBOL, NUMBER_OF_SHARES, VALUE_PAID) VALUES (?,?,?)" +
                    "ON CONFLICT (SYMBOL) DO UPDATE SET SYMBOL=EXCLUDED.SYMBOL, NUMBER_OF_SHARES=EXCLUDED.NUMBER_OF_SHARES, VALUE_PAID = EXCLUDED.VALUE_PAID" ;

    private static final String SELECT = "SELECT SYMBOL, NUMBER_OF_SHARES, VALUE_PAID FROM POSITION WHERE SYMBOL=?";

    private static final String SELECT_ALL = "SELECT * FROM POSITION";

    private static final String DELETE_ALL = "DELETE FROM POSITION";

    private static final String DELETE_BY_SYMBOL = "DELETE FROM POSITION WHERE SYMBOL=?";

    private static final String UPDATE = "UPDATE POSITION SET NUMBER_OF_SHARES =?, VALUE_PAID=? WHERE SYMBOL=?";

    final static Logger logger = LoggerFactory.getLogger(PositionDao.class);

    @Override
    public Position save(Position position) throws IllegalArgumentException {
        try {
            logger.info("The save details "+ position.toString());
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, position.getTicker());
            ps.setInt(2, position.getNumOfShares());
            ps.setDouble(3, position.getValuePaid());
            logger.info(ps.toString());
            logger.info("answer = " +ps.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return position;

    }

    @Override
    public Optional<Position> findById(String ticker) throws IllegalArgumentException {
        Position position = new Position();
        logger.info("The stock to find by ID from database" +ticker);
        try{
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT);
            ps.setString(1, ticker.toUpperCase());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            logger.info(rs.toString());
            while (rs.next()) {

                position.setTicker(rs.getString(1));
                position.setNumOfShares(rs.getInt(2));
                position.setValuePaid(rs.getDouble(3));

            }
            logger.info("" +position.getNumOfShares());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.of(position);
    }

    @Override
    public Iterable<Position> findAll() {
        List<Position> position = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            connection = ConnectionFactory.getConnection();
            ps = connection.prepareStatement(SELECT_ALL);
            logger.info(ps.toString());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Position position1 = new Position();
                position1.setTicker(rs.getString(1));
                position1.setNumOfShares( rs.getInt(2));
                position1.setValuePaid(rs.getDouble(3));
                position.add(position1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return position;
    }

    @Override
    public void deleteById(String ticker) throws IllegalArgumentException {
            logger.info("The stock to delete by ID " + ticker);
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_SYMBOL);
            ps.setString(1, ticker.toUpperCase());
            ps.execute();
            logger.info(ps.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteAll() {

        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_ALL);
            ps.execute();
            logger.info(ps.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(Position position) throws IllegalArgumentException {
        logger.info("The stock details to update " +position.toString());
        try {
            connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE);

        ps.setDouble(1, position.getNumOfShares());
        ps.setDouble(2, position.getValuePaid());
        ps.setString(3, position.getTicker());
        logger.info(ps.toString());
        ps.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    }

