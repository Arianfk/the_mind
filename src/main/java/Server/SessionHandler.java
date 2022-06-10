package Server;

import java.io.IOException;
import java.net.Socket;

public class SessionHandler extends Thread {
    private final Socket socket;
    private final ClientHandler clientHandler;
    private int numberOfPlayers;

    public SessionHandler(Socket socket) {
        this.socket = socket;
        this.clientHandler = new ClientHandler(socket);
    }

    @Override
    public void run() {
        super.run();

        clientHandler.sendMessage("Session Created\n");
        numberOfPlayers = Integer.parseInt(clientHandler.waitForMessage());

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
