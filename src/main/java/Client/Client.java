package Client;

import Connection.ConnectionHandler;
import Connection.Message;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ConnectionHandler connectionHandler;
    private byte[] authToken;

    public void connect() throws IOException {
        socket = new Socket("localhost", 80);
        connectionHandler = new ConnectionHandler(socket);

        Message message = connectionHandler.waitForMessage();
        authToken = message.getAuthToken();

        connectionHandler.sendMessage(new Message(authToken, "Token Received!?"));

        System.out.println(new String(connectionHandler.waitForMessage().getBody()));
    }
}
