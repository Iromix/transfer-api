package transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AccountDto implements Serializable {
    private int accountNumber;
    private MoneyDto moneyDto;

    private AccountDto() {
    }
}
