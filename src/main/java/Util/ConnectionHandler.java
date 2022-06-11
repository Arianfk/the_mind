package Util;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ConnectionHandler extends Thread {
    private final Socket socket;
    private final MyInputStream inputStream;
    private final MyOutputStream outputStream;
    private MessageRecieveListener messageReceiveListener;
    private byte[] authToken;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = new MyInputStream(socket.getInputStream());
            this.outputStream = new MyOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public MyInputStream getInputStream() {
        return inputStream;
    }

    public MyOutputStream getOutputStream() {
        return outputStream;
    }

    public MessageRecieveListener getMessageReceiveListener() {
        return messageReceiveListener;
    }

    public void setMessageReceiveListener(MessageRecieveListener messageRecieveListener) {
        this.messageReceiveListener = messageRecieveListener;
    }

    public void sendMessage(String message) {
        if (authToken != null) {
            try {
                outputStream.write(authToken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            outputStream.writeLine(message);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String waitForMessage() {
        if (authToken != null) {
            byte[] incomeToken = new byte[32];
            try {
                inputStream.read(incomeToken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (!Arrays.equals(incomeToken, authToken)) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Wrong Token";
            }
        }
        try {
            return inputStream.nextLine();
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
