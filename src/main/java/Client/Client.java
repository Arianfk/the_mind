package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            int n = 5;
            DataOutputStream[] outputStreams = new DataOutputStream[n];
            for (int i = 0; i < n; i++) {
                Socket socket = new Socket("localhost", 8080);
                outputStreams[i] = new DataOutputStream(socket.getOutputStream());
            }


            while (true) {
                int x = (int) Math.floor(Math.random() * n);
                outputStreams[x].writeUTF("Message By Client " + x);
                outputStreams[x].flush();
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
