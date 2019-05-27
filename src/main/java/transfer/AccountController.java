package transfer;

import io.javalin.Handler;
import transfer.dto.AccountDto;
import transfer.dto.MoneyDto;

import java.util.Objects;

public class AccountController {

    private final static AccountService accountService = AccountConfiguration.accountService();

    public static Handler createAccount = ctx -> {
        String currencyCode = Objects.requireNonNull(ctx.pathParam("code"));
        accountService.createAccount(currencyCode);
        ctx.status(201);
    };

    public static Handler depositMoney = ctx -> {
        Integer accountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("number")));
        MoneyDto moneyDto = ctx.bodyAsClass(MoneyDto.class);
        accountService.deposit(accountNumber, moneyDto);
        ctx.status(201);
    };

    public static Handler withdrawMoney = ctx -> {
        Integer accountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("number")));
        MoneyDto moneyDto = ctx.bodyAsClass(MoneyDto.class);
        accountService.withdraw(accountNumber, moneyDto);
        ctx.status(201);
    };

    public static Handler getAccount = ctx -> {
        Integer accountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("number")));
        AccountDto account = accountService.getAccount(accountNumber);
        ctx.json(account);
    };
}
