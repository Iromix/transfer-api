package transfer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AccountDto implements Serializable {
    private int accountNumber;
    private MoneyDto moneyDto;
}
