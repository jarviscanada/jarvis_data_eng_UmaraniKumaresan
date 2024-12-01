package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DAO.PositionDao;
import ca.jrvs.apps.stockquote.DAO.QuoteDao;
import ca.jrvs.apps.stockquote.DTO.Position;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PositionService {
    private PositionDao dao;
    final static Logger logger = LoggerFactory.getLogger(PositionService.class);

    /**
     * Processes a buy order and updates the database accordingly
     * @param ticker
     * @param numberOfShares
     * @param price
     * @return The position in our database after processing the buy
     * fetch the latest price by calling Alpha vantage API
     * Check if the stock already exists in the portfolio
     * if stock already exists add number of shares bought to the already existing shares
     * if stock already exists update otherwise insert
     *
     * */

    public Position buy(String ticker, int numberOfShares, double price) {
        //call vantage API get stock information.
        QuoteHttpHelper helper = new QuoteHttpHelper();
        Quote quote= helper.fetchQuoteInfo(ticker);
        //Once quote is fetched insert the same in quote table.
        QuoteDao quoteDao = new QuoteDao();
        quoteDao.save(quote);

        //check if stock already exists in the folio
        PositionDao positionDao = new PositionDao();
        Optional<Position> position = positionDao.findById(ticker);
        logger.info(position.toString());
        Position pos = position.orElse(new Position());
        pos.setTicker(quote.getTicker());
        pos.setNumOfShares(pos.getNumOfShares() + numberOfShares);
        pos.setValuePaid(pos.getValuePaid()+price);
        positionDao.save(pos);

        return pos;   //TO DO
    }

    public Position getPositionByTicker(String ticker) {
        System.out.println("getPositionByTicker: " + ticker);
        logger.info("getPositionByTicker: " + ticker);
        PositionDao positionDao = new PositionDao();
        Optional<Position> position = positionDao.findById(ticker);
        Position pos = position.orElse(new Position());
        return pos;

    }

    /**
     * Sells all shares of the given ticker symbol
     * @param ticker
     */
    public void sell(String ticker) {
        System.out.println("sell: " + ticker);
        PositionDao positionDao = new PositionDao();
        logger.info("sell: " + ticker);
                positionDao.deleteById(ticker);

    }

    public  Iterable<Position>  findAll(){
        PositionDao positionDao = new PositionDao();
        Iterable<Position> iterable =positionDao.findAll();
        logger.info(iterable.toString());
        return iterable;
    }

}
