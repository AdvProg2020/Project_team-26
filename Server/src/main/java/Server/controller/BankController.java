package Server.controller;

import com.google.gson.Gson;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotEnoughCreditException;
import exception.NotLoggedINException;
import model.Customer;
import model.Session;
import model.User;
import model.enums.Role;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

@RestController
public class BankController {

    private UserRepository userRepository;

    private String bankHost = "localhost";
    private int bankPort = 8090;
    private int storeId = 10001;
    private String storeToken;
    private String storeUsername = "Store";
    private String storePassword = "12345678";

    public BankController() {
        this.userRepository = (UserRepository) RepositoryContainer.getInstance().getRepository("UserRepository");
    }

    @PostMapping("/controller/method/bank/create-account")
    public String createAccount(@RequestBody Map info) throws IOException {
        String command = "create_account" + " " +
                info.get("firstname") + " " +
                info.get("lastname") + " " +
                info.get("username") + " " +
                info.get("password") + " " +
                info.get("repeat_password");
        return sendCommand(command);
    }

    @PostMapping("/controller/method/bank/getToken")
    public String getToken(@RequestBody Map info) throws IOException, InvalidTokenException {
        Session session = Session.getSession((String) info.get("token"));
        String command = "get_token" + " " +
                info.get("username") + " " +
                info.get("password");
        String result = sendCommand(command);
        session.setBankToken(result);
        return result;
    }

    @PostMapping("/controller/method/bank/chargeAccount")
    public String chargeAccount(@RequestBody Map info) throws InvalidTokenException, IOException, NotLoggedINException, NoAccessException {
        Session session = Session.getSession((String) info.get("token"));
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You must login first.");
        } else if (session.getLoggedInUser().getRole() == Role.ADMIN) {
            throw new NoAccessException("You must be a customer to charge account.");
        }
        String command = "create_receipt" + " " +
                session.getBankToken() + " move " +
                info.get("amount") + " " +
                info.get("userId") + " " +
                storeId + " " +
                info.get("description");
        String result = sendCommand(command);
        try {
            int receiptId = Integer.parseInt(result);
            result = sendCommand("pay " + receiptId);
            if (result.equals("done successfully")) {
                User user = session.getLoggedInUser();
                user.changeCredit(user.getCredit() + (long) info.get("amount"));
                userRepository.save(user);
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @PostMapping("/controller/method/bank/withdraw-from-account")
    public String withdrawFromAccount(@RequestBody Map info) throws InvalidTokenException, IOException, NotLoggedINException, NotEnoughCreditException {
        Session session = Session.getSession((String) info.get("token"));
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You must login first.");
        }
        User user = session.getLoggedInUser();
        Gson gson = new Gson();
        long amountPrice = gson.fromJson((String) info.get("amount"), Long.class);
        if (user.getCredit() - amountPrice < Session.getMinCredit()) {
            throw new NotEnoughCreditException("There must be " + Session.getMinCredit() + " left in your account.", user.getCredit());
        }
        storeToken = sendCommand("get_token " + storeUsername + " " + storePassword);
        String command = "create_receipt" + " " +
                storeToken + " move " +
                amountPrice + " " +
                storeId + " " +
                info.get("userId") + " " +
                info.get("description");
        String result = sendCommand(command);
        try {
            int receiptId = Integer.parseInt(result);
            result = sendCommand("pay " + receiptId);
            if (result.equals("done successfully")) {
                user.changeCredit(user.getCredit() - amountPrice);
                userRepository.save(user);
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    private String sendCommand(String command) throws IOException {
        Socket socket = new Socket(bankHost, bankPort);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(command);
        dataOutputStream.flush();
        String result = dataInputStream.readUTF();
        socket.close();
        System.out.println("\n\n" + command);
        System.out.println("\n\n" + result);
        return result;
    }
}
