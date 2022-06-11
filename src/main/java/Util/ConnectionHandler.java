package Util;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private final MyInputStream inputStream;
    private final MyOutputStream outputStream;
    private MessageRecieveListener messageReceiveListener;

    public ConnectionHandler(Socket socket) {
        try {
            this.inputStream = new MyInputStream(socket.getInputStream());
            this.outputStream = new MyOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            outputStream.writeLine(message);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String waitForMessage() {
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
