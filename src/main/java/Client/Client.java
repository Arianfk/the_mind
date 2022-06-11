package Client;

import Util.ConnectionHandler;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ConnectionHandler connectionHandler;
    private byte[] authToken;

    public void connect() throws IOException {
        socket = new Socket("localhost", 8080);
        connectionHandler = new ConnectionHandler(socket);
        authToken = new byte[32];
        connectionHandler.getInputStream().read(authToken);
        System.out.println(new String(authToken));
    }
}
