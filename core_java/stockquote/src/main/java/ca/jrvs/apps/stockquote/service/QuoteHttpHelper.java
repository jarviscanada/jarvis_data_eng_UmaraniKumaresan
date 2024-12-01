package ca.jrvs.apps.stockquote.service;
import ca.jrvs.apps.stockquote.DTO.GlobalQuoteDTO;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QuoteHttpHelper {
    final static Logger logger = LoggerFactory.getLogger(QuoteHttpHelper.class);


    public static void main(String[] args) {

String symbol = "MSFT";
QuoteHttpHelper helper = new QuoteHttpHelper();
helper.fetchQuoteInfo(symbol);
    }

    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json"))
                .header("X-RapidAPI-Key", "4f233cc6f1msh55df3814d1f8decp1ba6c2jsn678c171e4410")
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        Quote quote = new Quote();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
           logger.info(response.body());

            JsonParser parser = new JsonParser();
            GlobalQuoteDTO globalQuote = parser.jsonToObject(response.body(), GlobalQuoteDTO.class);
            quote = globalQuote.getQuote();
            logger.info(""+quote);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quote;
    }

}
