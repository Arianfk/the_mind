package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final BufferedReader inputStream;
    private final DataOutputStream outputStream;
    private MessageRecieveListener messageReceiveListener;

    public ClientHandler(Socket socket) {
        try {
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageRecieveListener getMessageReceiveListener() {
        return messageReceiveListener;
    }

    public void setMessageReceiveListener(MessageRecieveListener messageRecieveListener) {
        this.messageReceiveListener = messageRecieveListener;
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String waitForMessage() {
        try {
            return inputStream.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            String message = waitForMessage();
            if (messageReceiveListener != null) messageReceiveListener.onMessageRecieved(message);
        }
    }
}
