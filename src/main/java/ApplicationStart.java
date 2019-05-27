import io.javalin.Javalin;
import transfer.*;
import transfer.dto.AccountDto;
import transfer.dto.MoneyDto;

import java.util.Objects;

public class ApplicationStart {

    private static Javalin app;

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        TransferService transferService = TransferConfiguration.transferService();
        AccountService accountService = AccountConfiguration.accountService();

        app = Javalin.create().start(7000);
        app.get("/transfer", ctx -> ctx.result("Hello World"));
        app.post("/transfer/:from/:to", ctx -> {
            Integer fromAccountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("from")));
            Integer toAccountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("to")));
            MoneyDto moneyDto = ctx.bodyAsClass(MoneyDto.class);

            transferService.transfer(fromAccountNumber, toAccountNumber, moneyDto);

            ctx.status(201);
        });

        app.post("/account/currency/:code", ctx -> {
            String currencyCode = Objects.requireNonNull(ctx.pathParam("code"));

            accountService.createAccount(currencyCode);

            ctx.status(201);
        });

        app.post("/account/:number/deposit", ctx -> {
            Integer accountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("number")));
            MoneyDto moneyDto = ctx.bodyAsClass(MoneyDto.class);

            accountService.deposit(accountNumber, moneyDto);

            ctx.status(201);
        });

        app.post("/account/:number/withdraw", ctx -> {
            Integer accountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("number")));
            MoneyDto moneyDto = ctx.bodyAsClass(MoneyDto.class);

            accountService.withdraw(accountNumber, moneyDto);

            ctx.status(201);
        });

        app.get("/account/:number", ctx -> {
            Integer accountNumber = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("number")));

            AccountDto account = accountService.getAccount(accountNumber);

            ctx.json(account);
        });
    }

    public static void stopServer() {
        app.stop();
    }

}
