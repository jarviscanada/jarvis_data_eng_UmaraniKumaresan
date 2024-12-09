package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.DTO.Position;

import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.service.QuoteService;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class StockQuoteController {
    final static Logger logger = LoggerFactory.getLogger(StockQuoteController.class);
    private QuoteService quoteService;
    private PositionService positionService;

    /**
     * User interface for our application
     */
    public void initClient() {
        System.out.println("1 - View Stock Information");
        System.out.println("2 - Buy Stock ");
        System.out.println("3 - Sell Stock ");
        System.out.println("4 - View Portfolio");
        System.out.println("5 - Exit Application");
        BasicConfigurator.configure();
        Scanner scanner = new Scanner(System.in);

        int response = scanner.nextInt();
        logger.info("User input is ", response);

        switch (response) {
            case 1:
                viewStock(scanner);
                System.out.println("View Stock Information");
                break;
            case 2:
                buyStock(scanner);

                break;
            case 3:
               sellStock(scanner);
                break;
            case 4:
                viewPortfolio();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("PLease enter valid number");
                System.exit(0);
        }
    }


    private void buyStock(Scanner scanner) {

        System.out.println("Enter Stock Symbol");
        String symbol = scanner.next();
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
        Quote quo = quoteHttpHelper.fetchQuoteInfo(symbol);
        System.out.println("The price of one " + symbol + " share is " + quo.getPrice());

        System.out.println("Enter Number of shares");
        Integer numberOfShares = scanner.nextInt();

        Double price = numberOfShares*quo.getPrice();
        System.out.println("The price of " + numberOfShares + " shares of "+ symbol + " is " + price);
        logger.info("entered symbol" + symbol);

        System.out.println("Do you want to buy stock? Yes/No");
        String response = scanner.next();
        Position position = new Position();

        if ("yes".equalsIgnoreCase(response)) {
            positionService = new PositionService();
            position = positionService.buy(symbol, numberOfShares, price);
            System.out.println("Your stock purchase completed successfully. Now you hold " + position.getNumOfShares() +" shares worth " + position.getValuePaid());
            }
        else if ("No".equalsIgnoreCase(response)) {
            System.out.println("Thank you");
            System.exit(0);
        }
        else {
            System.out.println("Invalid input");
        }
    }

    private void sellStock(Scanner scanner) {

        System.out.println("Enter symbol");
        String symbol = scanner.next();
        logger.info("stock symbol" + symbol);
        PositionService positionService = new PositionService();
        Position ps = positionService.getPositionByTicker(symbol);
        logger.info("stock info" + ps.toString());
        System.out.println("You have "+ ps.getNumOfShares()  + " shares of " +ps.getTicker() + " worth " + ps.getValuePaid());

        System.out.println("Do you want to sell stock? Yes/No");
        String response = scanner.next();
        if ("yes".equalsIgnoreCase(response)) {
            positionService.sell(symbol);

        }

    }

    private void viewStock(Scanner scanner) {
        //get the stock symbol from user
        //fetch the stock price from API
        //print the information to the customer
        //fetch the available position from the database for that stock.
        //print the information to the customer.
        System.out.println("Enter stock symbol to view stock information");
        String symbol = scanner.next();
        logger.info("View Stock Information for"+ symbol);
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
        Quote quote = quoteHttpHelper.fetchQuoteInfo(symbol);
        System.out.println("The Current price of the stock " + symbol + " is " + quote.getPrice());
        PositionService positionService = new PositionService();
        Position ps = positionService.getPositionByTicker(symbol);
        System.out.println("you have " + ps.getNumOfShares() +" " + symbol + " Shares worth " + ps.getValuePaid());
    }

    private void viewPortfolio(){

        PositionService positionService = new PositionService();
        Iterable<Position> iterable= positionService.findAll();
        logger.info("View Portfolio" + iterable);
        for (Position position : iterable) {
            System.out.println(position.getTicker() + " " + position.getNumOfShares());
        }

    }
}
