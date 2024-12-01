package ca.jrvs.apps.stockquote.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalQuoteDTO {

    @JsonProperty("Global Quote")
    private Quote quote;

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
