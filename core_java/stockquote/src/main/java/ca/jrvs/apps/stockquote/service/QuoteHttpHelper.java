package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DTO.GlobalQuoteDTO;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.pattern.PropertiesPatternConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class QuoteHttpHelper {

    final static Logger logger = LoggerFactory.getLogger(QuoteHttpHelper.class);

    private final HttpClient httpClient;
    private static String url;
    private static String apiKey;
    private static String host;

    static {
        {
            Properties configuration = new Properties();
            InputStream inputStream = PropertiesPatternConverter.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");
            try {
                configuration.load(inputStream);
                url = configuration.getProperty("api.url");
                apiKey = configuration.getProperty("api.key");
                host = configuration.getProperty("api.host");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

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
                .uri(URI.create( url+ symbol + "&datatype=json"))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", host)
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
