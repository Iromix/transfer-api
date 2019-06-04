package transfer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class MoneyDto implements Serializable {
    private double amount;
    private String currencyCode;

    private MoneyDto() {
        //need for jackson serialization
    }
}
