package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DAO.QuoteDao;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class QuoteService {
    private QuoteDao dao;
    private QuoteHttpHelper httpHelper;
    final static Logger logger = LoggerFactory.getLogger(QuoteService.class);

    /**
     * Fetches latest quote data from endpoint
     * @param ticker
     * @return Latest quote information or empty optional if ticker symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
        //TO DO
    return null;
    }

}
