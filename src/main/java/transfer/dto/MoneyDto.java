package transfer.dto;

import java.io.Serializable;

public class MoneyDto implements Serializable {
    private double amount;
    private String currencyCode;

    public MoneyDto() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
