package ca.jrvs.apps.stockquote.DTO;

public class Position {

    private String ticker; //id
    private int numOfShares;
    private double valuePaid;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public double getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(double valuePaid) {
        this.valuePaid = valuePaid;
    }
}
