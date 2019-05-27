package transfer.web;

import io.javalin.Handler;
import transfer.configuration.TransferConfiguration;
import transfer.domain.TransferService;
import transfer.domain.dto.MoneyDto;

import java.util.Objects;

public class TransferController {

    private static final TransferService transferService = TransferConfiguration.transferService();

    public static Handler transfer = ctx -> {
        Integer fromAccountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("from")));
        Integer toAccountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("to")));
        MoneyDto moneyDto = ctx.bodyAsClass(MoneyDto.class);

        transferService.transfer(fromAccountNumber, toAccountNumber, moneyDto);

        ctx.status(201);
    };
}
