package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DAO.QuoteDao;
import ca.jrvs.apps.stockquote.DTO.Quote;
import junit.framework.TestCase;

import java.util.Optional;

public class QuoteHttpHelperTest extends TestCase {

       public void testFetchQuoteInfo() {
           String symbol = "BTC";
        QuoteHttpHelper helper = new QuoteHttpHelper();
        Quote quo = helper.fetchQuoteInfo(symbol);
        assertTrue("Stock service return error ",quo.getTicker().equals(symbol));
    }
}