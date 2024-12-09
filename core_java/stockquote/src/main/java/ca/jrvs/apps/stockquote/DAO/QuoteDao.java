package ca.jrvs.apps.stockquote.DAO;

import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class QuoteDao implements CrudDao<Quote, String> {
    private Connection connection;
    private static final String INSERT =
            "INSERT INTO QUOTE (SYMBOL, OPEN, HIGH, LOW, PRICE, VOLUME, LATEST_TRADING_DAY, PREVIOUS_CLOSE, CHANGE, CHANGE_PERCENT, TIMESTAMP) " +
                    "VALUES (?,?,?, ?,?,?, ?,?,?, ?,current_timestamp) ON CONFLICT (SYMBOL) DO UPDATE SET SYMBOL = EXCLUDED.SYMBOL, OPEN=EXCLUDED.OPEN, HIGH=EXCLUDED.HIGH, " +
                    "LOW =EXCLUDED.LOW, PRICE= EXCLUDED.PRICE, VOLUME =EXCLUDED.VOLUME, LATEST_TRADING_DAY= EXCLUDED.LATEST_TRADING_DAY, PREVIOUS_CLOSE = EXCLUDED.PREVIOUS_CLOSE, " +
                    "CHANGE=EXCLUDED.CHANGE, CHANGE_PERCENT=EXCLUDED.CHANGE_PERCENT ,TIMESTAMP=CURRENT_TIMESTAMP";
    private static final String SELECT =
            "SELECT SYMBOL, OPEN, HIGH, LOW, PRICE, VOLUME, LATEST_TRADING_DAY, PREVIOUS_CLOSE, CHANGE, CHANGE_PERCENT, TIMESTAMP  FROM QUOTE WHERE SYMBOL=?";

    private static final String SELECT_ALL = "SELECT * FROM QUOTE";

    private static final String DELETE_ALL = "DELETE FROM QUOTE";

    private static final String DELETE_BY_SYMBOL = "DELETE FROM QUOTE WHERE SYMBOL=?";

    final static Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    @Override
    public Quote save(Quote quote) throws IllegalArgumentException {
        logger.info("The quote details are " + quote);
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT);

            ps.setString(1, quote.getTicker());
            ps.setDouble(2, quote.getOpen());
            ps.setDouble(3, quote.getHigh());
            ps.setDouble(4, quote.getLow());
            ps.setDouble(5, quote.getPrice());
            ps.setDouble(6, quote.getVolume());
            ps.setDate(7, quote.getLatestTradingDay());
            ps.setDouble(8, quote.getPreviousClose());
            ps.setDouble(9, quote.getChange());
            ps.setString(10, quote.getChangePercent());

            ps.executeUpdate();
            logger.info("From save" + ps.toString());
            logger.info(ps.toString());
            return quote;

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

    }

    @Override
    public Optional<Quote> findById(String ticker) throws IllegalArgumentException {
        Quote quote = new Quote();
        logger.info("The quote details to find by id"+ ticker);
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT);
            ps.setString(1, ticker);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            logger.info(ps.toString());
            while (rs.next()) {

                quote.setTicker(rs.getString(1));
                quote.setOpen(rs.getDouble(2));
                quote.setHigh(rs.getDouble(3));
                quote.setLow(rs.getDouble(4));
                quote.setPrice(rs.getDouble(5));
                quote.setVolume(rs.getInt(6));
                quote.setLatestTradingDay(rs.getDate(7));
                quote.setPreviousClose(rs.getDouble(8));
                quote.setChange(rs.getDouble(9));
                quote.setChangePercent(rs.getString(10));
                quote.setTimestamp(rs.getTimestamp(11));
            logger.info("The quote details are "+quote.toString());
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
        return Optional.of(quote);
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> quotes = new ArrayList<>();
        PreparedStatement ps = null;
        try {

            connection = ConnectionFactory.getConnection();
            ps = connection.prepareStatement(SELECT_ALL);

            ps.execute();
            ResultSet rs = ps.getResultSet();
            logger.info(rs.toString());
            while (rs.next()) {
                Quote quote = new Quote();
                quote.setTicker(rs.getString(1));
                quote.setOpen(rs.getDouble(2));
                quote.setHigh(rs.getDouble(3));
                quote.setLow(rs.getDouble(4));
                quote.setPrice(rs.getDouble(5));
                quote.setVolume(rs.getInt(6));
                quote.setLatestTradingDay(rs.getDate(7));
                quote.setPreviousClose(rs.getDouble(8));
                quote.setChange(rs.getDouble(9));
                quote.setChangePercent(rs.getString(10));
                quote.setTimestamp(rs.getTimestamp(11));
                quotes.add(quote);
            logger.info(quote.toString());
            }
        logger.info("Total number of stockquotes"+ quotes.size());
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
        return quotes;
    }

    @Override
    public void deleteById(String ticker) throws IllegalArgumentException {
        Connection connection = null;
        Quote quote = new Quote();
        try {
            connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_SYMBOL);
            ps.setString(1, ticker);
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
}
