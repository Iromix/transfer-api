package transfer.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Money {

    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");
    static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal value;

    private final String currencyCode;

    Money(String currencyCode) {
        this(BigDecimal.ZERO, currencyCode);
    }

    Money(double value, String currencyCode) {
        this(new BigDecimal(value), currencyCode);
    }

    Money(BigDecimal value, Currency currency) {
        this(value, currency.getCurrencyCode());
    }

    Money(BigDecimal value) {
        this(value, DEFAULT_CURRENCY);
    }

    private Money(BigDecimal value, String currencyCode) {
        validateCurrencyCodeIsAvailable(currencyCode);

        this.value = value;
        this.currencyCode = currencyCode;
    }

    private void validateCurrencyCodeIsAvailable(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new CurrencyMismatchException("Unavailable currency code");
        }
    }

    double getValue() {
        return value.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return compatibleCurrency(money) &&
                Objects.equals(currencyCode, money.currencyCode);
    }

    public Money subtract(Money money) {
        if (incompatibleCurrency(money))
            throw new CurrencyMismatchException("Currency mismatch");

        return new Money(value.subtract(money.value), money.getCurrencyCode());
    }

    public Money add(Money money) {
        if (incompatibleCurrency(money))
            throw new CurrencyMismatchException("Currency mismatch");

        return new Money(value.add(money.value), money.getCurrencyCode());
    }

    private boolean compatibleCurrency(Money money) {
        return isZero(value) || isZero(money.value) || currencyCode.equals(money.getCurrencyCode());
    }

    private boolean incompatibleCurrency(Money money) {
        return !compatibleCurrency(money);
    }

    private boolean isZero(BigDecimal testedValue) {
        return BigDecimal.ZERO.compareTo(testedValue) == 0;
    }

    String getCurrencyCode() {
        return currencyCode;
    }

    boolean greaterThanZero() {
        return value.compareTo(Money.ZERO.value) > 0;
    }

    boolean lessThan(Money other) {
        return value.compareTo(other.value) < 0;
    }

}


