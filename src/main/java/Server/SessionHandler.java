package Server;

import Util.ConnectionHandler;

import java.io.IOException;
import java.net.Socket;

public class SessionHandler extends Thread {
    private final Socket socket;
    private final ConnectionHandler clientHandler;
    private int numberOfPlayers;

    public SessionHandler(Socket socket) {
        this.socket = socket;
        this.clientHandler = new ConnectionHandler(socket);
    }

    @Override
    public void run() {
        super.run();

        clientHandler.sendMessage("Session Created\n");
        //numberOfPlayers = Integer.parseInt(clientHandler.waitForMessage());
        String s = clientHandler.waitForMessage();
        System.out.println(s);
        //System.out.println(numberOfPlayers);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
