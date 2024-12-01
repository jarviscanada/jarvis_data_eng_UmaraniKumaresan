package ca.jrvs.apps.stockquote.main;

import ca.jrvs.apps.stockquote.controller.StockQuoteController;


public class StockMain {
    public static void main(String[] args) {
        System.out.println("Welcome to Stock Quote App! Choose Option");
        StockQuoteController controller = new StockQuoteController();

        controller.initClient();
    }

}
