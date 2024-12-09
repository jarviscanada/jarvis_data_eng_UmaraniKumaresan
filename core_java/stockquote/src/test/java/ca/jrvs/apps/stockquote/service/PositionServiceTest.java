package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DAO.PositionDao;
import ca.jrvs.apps.stockquote.DTO.Position;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class PositionServiceTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

        PositionService service = new PositionService();
        PositionDao dao = new PositionDao();
        dao.deleteById("AAPL");
        dao.deleteById("TSLA");
    }

    @Test
    public void testBuy() {
        String symbol = "AAPL";
        PositionService service = new PositionService();
        service.buy(symbol, 5, 78);
        PositionDao dao = new PositionDao();
        Optional<Position> pos = dao.findById(symbol);
        assertTrue("Buy passed",pos.isPresent());
    }

    @Test
    public void testGetPositionByTicker() {
        PositionService service = new PositionService();
        service.buy("AAPL", 5, 78);
       Position pos = service.getPositionByTicker("AAPL");
        assertTrue("getPositionByTicker passed","AAPL".equals(pos.getTicker()));
    }

    @Test
    public void testSell() {
        String symbol = "AAPL";
        PositionService service = new PositionService();
        service.buy(symbol, 5, 78);
        service.sell(symbol);
        Position pos = service.getPositionByTicker(symbol);
        assert pos.getTicker() == null;

    }

    @Test
    public void testFindAll() {
        PositionService service = new PositionService();
       service.buy("AAPL", 5, 78);
       service.buy("TSLA", 50, 7);
       Iterable<Position> pos =service.findAll();
       int count = 0;
       for (Position po : pos) {
           if ("TSLA".equals(po.getTicker())) {
               count ++;
           } else if ("AAPL".equals(po.getTicker())) {
               count ++;
           }
       }
        assertEquals("There should be 2 stocks listed",2,count);
        assertTrue("findAll method failed", pos.iterator().hasNext());
    }
}