package Server;

import Connection.ConnectionHandler;
import Connection.Message;
import Connection.MessageReceiveListener;

import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;

public class SessionHandler extends Thread {
    private final Socket socket;
    private final ConnectionHandler connectionHandler;
    private final byte[] authToken;
    private int numberOfPlayers;

    public SessionHandler(Socket socket) {
        this.socket = socket;
        this.connectionHandler = new ConnectionHandler(socket);

        this.authToken = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(authToken);
    }

    @Override
    public void run() {
        super.run();

        // Sending AuthToken
        connectionHandler.sendMessage(new Message(authToken));

        connectionHandler.setMessageReceiveListener(new MessageReceiveListener() {
            @Override
            public void onMessageReceived(Message message) {
                System.out.println(new String(message.getBody()));
            }
        });

        connectionHandler.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
