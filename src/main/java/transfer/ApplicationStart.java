package transfer;

import io.javalin.Javalin;
import transfer.domain.CurrencyMismatchException;
import transfer.domain.TransferException;
import transfer.web.AccountController;
import transfer.web.TransferController;

import java.util.NoSuchElementException;

public class ApplicationStart {

    private static Javalin app;

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        app = Javalin.create().start(7000);

        app.post("/transfer/:from/:to", TransferController.transfer);
        app.post("/account/currency/:code", AccountController.createAccount);
        app.post("/account/:number/deposit", AccountController.depositMoney);
        app.post("/account/:number/withdraw", AccountController.withdrawMoney);
        app.get("/account/:number", AccountController.getAccount);

        app.exception(NoSuchElementException.class, (e, ctx) -> {
            ctx.status(404);
            ctx.result(e.getMessage());
        }).exception(TransferException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        }).exception(CurrencyMismatchException.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result(e.getMessage());
        });
    }

    public static void stopServer() {
        app.stop();
    }

}
