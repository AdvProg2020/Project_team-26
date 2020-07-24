package client.connectionController;

import client.connectionController.interfaces.IBankController;
import client.exception.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class BankController implements IBankController {

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
    public String chargeAccount(@RequestBody Map info) throws InvalidTokenException, IOException {
        Session session = Session.getSession((String) info.get("token"));

        String command = "create_receipt" + " " +
                session.getBankToken() + " move " +
                info.get("userId") + " " +
                storeId + " " +
                info.get("description");
        String result = sendCommand(command);
        try {
            int receiptId = Integer.parseInt(result);
            return sendCommand("pay " + receiptId);
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
        return dataInputStream.readUTF();
    }
}
