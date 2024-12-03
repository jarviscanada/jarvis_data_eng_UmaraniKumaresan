package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DTO.GlobalQuoteDTO;
import ca.jrvs.apps.stockquote.DTO.Quote;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class QuoteHttpHelperMockTest {

    @Test
    public void testFetchQuoteInfo() throws Exception {
        // Arrange
        String mockSymbol = "MSFT";
        String mockResponseBody = "{\n" +
                "  \"Global Quote\": {\n" +
                "    \"01. symbol\": \"MSFT\",\n" +
                "    \"02. open\": \"330.00\",\n" +
                "    \"03. high\": \"335.00\",\n" +
                "    \"04. low\": \"328.00\",\n" +
                "    \"05. price\": \"332.50\",\n" +
                "    \"06. volume\": \"15000000\",\n" +
                "    \"07. latest trading day\": \"2024-12-01\",\n" +
                "    \"08. previous close\": \"331.00\",\n" +
                "    \"09. change\": \"1.50\",\n" +
                "    \"10. change percent\": \"0.45%\"\n" +
                "  }\n" +
                "}";

        // Mocking HttpClient and HttpResponse
        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse<String> mockHttpResponse = Mockito.mock(HttpResponse.class);

        // Setting up mock behavior
        when(mockHttpResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        // Creating a QuoteHttpHelper instance with the mocked HttpClient
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(mockHttpClient);

        // Act
        Quote result = quoteHttpHelper.fetchQuoteInfo(mockSymbol);

        // Assert
        assertEquals("MSFT", result.getTicker());
        assertEquals(330.00, result.getOpen(), 0.01);
        assertEquals(335.00, result.getHigh(), 0.01);
        assertEquals(328.00, result.getLow(), 0.01);
        assertEquals(332.50, result.getPrice(), 0.01);
        assertEquals(15000000, result.getVolume());
        assertEquals(331.00, result.getPreviousClose(), 0.01);
        assertEquals(1.50, result.getChange(), 0.01);
        assertEquals("0.45%", result.getChangePercent());

    }
}
