package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static ServerSocket serverSocket;
    static List<SessionHandler> sessions = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(80);
            while (true) {
                SessionHandler newSession = new SessionHandler(serverSocket.accept());
                sessions.add(newSession);
                newSession.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
