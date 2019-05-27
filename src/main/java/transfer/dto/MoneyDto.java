package transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MoneyDto implements Serializable {
    private double amount;
    private String currencyCode;

    private MoneyDto() {
    }
}
