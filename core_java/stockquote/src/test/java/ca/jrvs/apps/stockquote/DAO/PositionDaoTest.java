package ca.jrvs.apps.stockquote.DAO;

import ca.jrvs.apps.stockquote.DTO.Position;
import ca.jrvs.apps.stockquote.DTO.Quote;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


public class PositionDaoTest extends TestCase {
    Connection connection;

    @Before
    public void setUp() throws Exception {

        QuoteDao quoteDao = new QuoteDao();
        Quote quote = new Quote();
        quote.setTicker("GOOGL");
        quote.setVolume(3);
        quote.setChange(78);
        quote.setHigh(8);
        quote.setLow(9);
        quote.setOpen(10);
        quote.setPrice(78);
        quote.setChangePercent("34");
        quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        quote.setNumOfShares(45);
        quote.setValuePaid(89);
        Quote quote2 = new Quote();
        quote2.setTicker("TSLA");
        quote2.setVolume(30);
        quote2.setChange(789);
        quote2.setHigh(80);
        quote2.setLow(19);
        quote2.setOpen(4);
        quote2.setPrice(8);
        quote2.setChangePercent("4");
        quote2.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        quote2.setNumOfShares(4);
        quote2.setValuePaid(9);
        quoteDao.save(quote);
        quoteDao.save(quote2);
    }

    @After
    public void tearDown() throws Exception {
        PositionDao positionDao = new PositionDao();
        positionDao.deleteById("GOOGL");
        positionDao.deleteById("TSLA");
        QuoteDao quoteDao = new QuoteDao();
        quoteDao.deleteById("GOOGL");
        quoteDao.deleteById("TSLA");

        }

    @Test
    public void testSave() {
        PositionDao testDao = new PositionDao();
        Position position = new Position();
        position.setTicker("GOOGL");
        position.setNumOfShares(5);
        position.setValuePaid(700);
        testDao.save(position);
       Optional<Position> optionalPosition = testDao.findById(position.getTicker());
       assertTrue(optionalPosition.isPresent());
    }

    @Test
    public void testFindById() {
        PositionDao testDao = new PositionDao();
        Position position = new Position();
        position.setTicker("GOOGL");
        position.setNumOfShares(5);
        position.setValuePaid(700);
        testDao.save(position);

        Optional<Position> optionalPosition = testDao.findById(position.getTicker());
        assertEquals(optionalPosition.get().getNumOfShares(), 5);
        assertTrue(optionalPosition.isPresent());
        }

    @Test
    public void testFindAll() {
        PositionDao testDao = new PositionDao();
        Position position1 = new Position();
        position1.setTicker("GOOGL");
        position1.setNumOfShares(5);
        position1.setValuePaid(700);
        testDao.save(position1);

        Position position2 = new Position();
        position2.setTicker("TSLA");
        position2.setNumOfShares(500);
        position2.setValuePaid(5000);
        testDao.save(position2);
        Iterable<Position> pos = testDao.findAll();
        int count = 0;
        for (Position position : pos) {
             if ("TSLA".equals(position.getTicker())) {
                count ++;
                } else if ("GOOGL".equals(position.getTicker())) {
                 count ++;
             }
        }
        assertEquals("There should be 2 stocks listed",2,count);
    }

    @Test
    public void testDeleteById() {
        Position position = new Position();
        position.setTicker("GOOGL");
        position.setNumOfShares(5);
        position.setValuePaid(700);

        PositionDao testDao = new PositionDao();
       testDao.save(position);
       testDao.deleteById(position.getTicker());
       Optional<Position> pos =testDao.findById(position.getTicker());
       assertTrue(pos.isPresent());

    }

    @Test
    public void testDeleteAll() {
        PositionDao testDao = new PositionDao();
        Position position1 = new Position();
        position1.setTicker("GOOGL");
        position1.setNumOfShares(5);
        position1.setValuePaid(700);

        Position position2 = new Position();
        position2.setTicker("TSLA");
        position2.setNumOfShares(500);
        position2.setValuePaid(5000);
        testDao.save(position1);
        testDao.save(position2);
        testDao.deleteAll();
        Iterable<Position> pos = testDao.findAll();
        assertFalse("Deletion failed, All rows are not deleted",pos.iterator().hasNext());
   }

    @Test
    public void testUpdate() {
        PositionDao testDao = new PositionDao();
        Position position1 = new Position();
        position1.setTicker("GOOGL");
        position1.setNumOfShares(5);
        position1.setValuePaid(700);
        testDao.save(position1);
        testDao.update(position1);
        Optional<Position> pos = testDao.findById(position1.getTicker());
        assertEquals("Update Failed ", 5 , pos.get().getNumOfShares());
    }
}