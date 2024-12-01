package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DTO.Quote;
import junit.framework.TestCase;
import net.bytebuddy.agent.VirtualMachine;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.mockito.Mockito.*;

public class QuoteHttpHelperMockTest extends TestCase {
    private QuoteHttpHelper quoteHttpHelper;
    private HttpClient mockClient;
    private OkHttpClient client;
    private VirtualMachine.ForHotSpot.Connection.Response mockResponse;
    private ResponseBody mockResponseBody;

    @Mock
    HttpResponse<InputStream> mockResponseHttpResponse;
    @RunWith(MockitoJUnitRunner.class)

    @Spy
    HttpClient httpClient;

    public void test() throws Exception {
        when(httpClient.send(any(), any(HttpResponse.BodyHandlers.ofInputStream().getClass())))
                .thenReturn(mockResponseHttpResponse);

    HttpClient client = HttpClient.newHttpClient();

    @Mock
    public void setUp() throws Exception {

        mockClient = mock(HttpClient.class);
        mockResponse = mock(VirtualMachine.ForHotSpot.Connection.Response.class);
        mockResponseBody = mock(ResponseBody.class);
        HttpClient mockClient = mock(HttpClient.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));


    }

    public void tearDown() throws Exception {
    }

    public void testFetchQuoteInfo() {
    }
}