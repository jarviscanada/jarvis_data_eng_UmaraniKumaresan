package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DTO.GlobalQuoteDTO;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QuoteHttpHelper {

    final static Logger logger = LoggerFactory.getLogger(QuoteHttpHelper.class);

    private final HttpClient httpClient;

    // Default constructor to use default HttpClient
    public QuoteHttpHelper() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // Constructor with HttpClient injection for testing and custom configurations
    public QuoteHttpHelper(HttpClient httpClient) {
        this.httpClient = httpClient != null ? httpClient : HttpClient.newHttpClient();
    }

    // Method to fetch quote information
    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Symbol cannot be null or empty");
        }

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json"))
                .header("X-RapidAPI-Key", "4f233cc6f1msh55df3814d1f8decp1ba6c2jsn678c171e4410")
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .GET()
                .build();

        try {
            // Send the request and handle the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Received response: " + response.body());

            // Parse the JSON response
            JsonParser parser = new JsonParser();
            GlobalQuoteDTO globalQuote = parser.jsonToObject(response.body(), GlobalQuoteDTO.class);

            // Convert to Quote object and return
            Quote quote = globalQuote.getQuote();
            logger.info("Parsed Quote: " + quote);
            return quote;

        } catch (Exception e) {
            logger.error("IO error while fetching quote data", e);
        }

        // Return an empty Quote in case of failure
        return new Quote();
    }
}
