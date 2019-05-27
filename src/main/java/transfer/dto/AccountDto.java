package transfer.dto;

import java.io.Serializable;

public class AccountDto implements Serializable {
    private int accountNumber;
    private MoneyDto moneyDto;

    public AccountDto() {
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public MoneyDto getMoneyDto() {
        return moneyDto;
    }

    public void setMoneyDto(MoneyDto moneyDto) {
        this.moneyDto = moneyDto;
    }
}
