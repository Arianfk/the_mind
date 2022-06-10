package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);

            List<Socket> sockets = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);

                Runnable runnable = () -> {
                    try {
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        while (true) {
                            System.out.println(inputStream.readUTF());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };

                (new Thread(runnable)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
