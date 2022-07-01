package Server;

import Connection.ConnectionHandler;
import Connection.JsonRoom;
import Connection.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessionHandler extends Thread {
    private final Socket socket;
    private final ConnectionHandler connectionHandler;
    private final byte[] authToken;
    private String name;
    private Room room;

    public SessionHandler(Socket socket) {
        this.socket = socket;
        this.connectionHandler = new ConnectionHandler(socket);

        this.authToken = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(authToken);

        connectionHandler.setAuthToken(authToken);
    }

    @Override
    public void run() {
        super.run();

        // Sending AuthToken
        connectionHandler.sendMessage(new Message(authToken));

        // Receiving Name
        this.name = new String(connectionHandler.waitForMessage().getBody());

        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();

        connectionHandler.setMessageReceiveListener(message -> {
            if (!Arrays.equals(message.getAuthToken(), authToken)) {
                connectionHandler.sendMessage(new Message((byte) 0xff));
                return;
            }

            switch (message.getHeader()) {
                case 0x01 -> {
                    List<JsonRoom> jsonRooms = new ArrayList<>();
                    for (Room room1 : Server.rooms) {
                        if (!room1.isStarted() && room1.getPlayers().size() < room1.getMaximumNumberOfPlayers())
                            jsonRooms.add(new JsonRoom(room1));
                    }

                    connectionHandler.sendWithAT(new Message(gson.toJson(jsonRooms)));
                }
            }
        });

        connectionHandler.start();
    }
}
