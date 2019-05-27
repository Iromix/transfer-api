import io.javalin.Javalin;
import transfer.AccountController;
import transfer.TransferController;

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
    }

    public static void stopServer() {
        app.stop();
    }

}
