package transfer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Money {

    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");
    static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal value;

    private final String currencyCode;

    Money() {
        this(BigDecimal.ZERO, Money.DEFAULT_CURRENCY);
    }

    Money(BigDecimal value, Currency currency) {
        this(value, currency.getCurrencyCode());
    }

    Money(BigDecimal value) {
        this(value, DEFAULT_CURRENCY);
    }

    private Money(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
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
            throw new IllegalArgumentException("Currency mismatch");

        return new Money(value.subtract(money.value), money.getCurrencyCode());
    }

    public Money add(Money money) {
        if (incompatibleCurrency(money))
            throw new IllegalArgumentException("Currency mismatch");

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

    private String getCurrencyCode() {
        return currencyCode;
    }

    boolean greaterThan(Money other) {
        return value.compareTo(other.value) > 0;
    }

    boolean lessThan(Money other) {
        return value.compareTo(other.value) < 0;
    }

}


