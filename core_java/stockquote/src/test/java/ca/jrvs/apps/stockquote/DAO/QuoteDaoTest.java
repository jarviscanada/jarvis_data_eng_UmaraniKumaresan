package ca.jrvs.apps.stockquote.DAO;

import ca.jrvs.apps.stockquote.DTO.Position;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.service.QuoteHttpHelper;
import junit.framework.TestCase;
import okhttp3.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class QuoteDaoTest extends TestCase {

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

        QuoteDao dao = new QuoteDao();
        dao.deleteAll();
        Iterable<Quote> quote = dao.findAll();
        assertFalse("Delete all failed, records available in the database", quote.iterator().hasNext());
    }

    @Test
    public void testSave() {
        QuoteDao dao = new QuoteDao();
        Quote quote = new Quote();
        quote.setTicker("TSLA");
        quote.setVolume(30);
        quote.setChange(78);
        quote.setHigh(80);
        quote.setLow(19);
        quote.setOpen(4);
        quote.setPrice(8);
        quote.setChangePercent("4");
        quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        quote.setNumOfShares(4);
        quote.setValuePaid(9);
        dao.save(quote);
        assertEquals("save Method failed", quote.getTicker(), "TSLA");
    }

    @Test
    public void testFindById() {
        QuoteDao dao = new QuoteDao();
        Quote quote = new Quote();
        quote.setTicker("TSLA");
        quote.setVolume(30);
        quote.setChange(78);
        quote.setHigh(80);
        quote.setLow(19);
        quote.setOpen(4);
        quote.setPrice(8);
        quote.setChangePercent("4");
        quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        quote.setNumOfShares(4);
        quote.setValuePaid(9);
        dao.save(quote);
        Optional<Quote> quotes = dao.findById(quote.getTicker());
        assertTrue("findById method failed", quotes.isPresent());
    }

    @Test
    public void testFindAll() {
        QuoteDao dao = new QuoteDao();
        Quote quote = new Quote();
        quote.setTicker("TSLA");
        quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        QuoteHttpHelper helper = new QuoteHttpHelper();
        Quote quotes = helper.fetchQuoteInfo(quote.getTicker());
        dao.save(quotes);

        Quote quote1 = new Quote();
        quote1.setTicker("AAPL");
        quote1.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        quote1 = helper.fetchQuoteInfo(quote1.getTicker());
        dao.save(quote1);
        Iterable<Quote> quoteIterable = dao.findAll();
        int count = 0;

        for (Quote quo : quoteIterable) {
            if ("TSLA".equals(quo.getTicker())) {
                count ++;
            } else if ("GOOGL".equals(quo.getTicker())) {
                count ++;
            }
        }
        assertEquals("There should be 2 stocks listed",2,count);
        assertTrue("findAll method failed", quoteIterable.iterator().hasNext());

    }

    @Test
    public void testDeleteById() {
        QuoteDao dao = new QuoteDao();
        Quote quote = new Quote();
        quote.setTicker("TSLA");
        quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
        QuoteHttpHelper helper = new QuoteHttpHelper();
        Quote quotes = helper.fetchQuoteInfo(quote.getTicker());
        dao.save(quotes);

        dao.deleteById(quote.getTicker());
        Optional<Quote>  optional = dao.findById(quote.getTicker());
        assertTrue("delete method failed", optional.isPresent());
    }

    @Test
    public void testDeleteAll() {
        QuoteDao dao = new QuoteDao();
        Quote quote = new Quote();
        dao.deleteAll();
        Iterable<Quote> quo = dao.findAll();
        assertFalse("delete method failed", quo.iterator().hasNext());

    }

}