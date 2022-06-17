package Connection;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ConnectionHandler extends Thread {
    private final Socket socket;
    private byte[] authToken;
    private MessageReceiveListener messageReceiveListener;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public byte[] getAuthToken() {
        return authToken;
    }

    public void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public MessageReceiveListener getMessageReceiveListener() {
        return messageReceiveListener;
    }

    public void setMessageReceiveListener(MessageReceiveListener messageRecieveListener) {
        this.messageReceiveListener = messageRecieveListener;
    }

    public void sendMessage(Message message) {
        try {
            socket.getOutputStream().write(message.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendWithAT(Message message) {
        message.setAuthToken(authToken);
        sendMessage(message);
    }

    public Message waitForMessage() {
        Message message = new Message();
        try {
            byte[] authTokenAndHeaderAndLength = new byte[Message.AUTH_TOKEN_LENGTH + 5];
            socket.getInputStream().read(authTokenAndHeaderAndLength);

            byte[] tmp = new byte[4];
            System.arraycopy(authTokenAndHeaderAndLength, Message.AUTH_TOKEN_LENGTH + 1, tmp, 0, 4);
            int bodyLen = ByteBuffer.wrap(tmp).getInt();

            byte[] body = new byte[bodyLen];
            socket.getInputStream().read(body);

            byte[] bytes = new byte[authTokenAndHeaderAndLength.length + bodyLen];
            System.arraycopy(authTokenAndHeaderAndLength, 0, bytes, 0, authTokenAndHeaderAndLength.length);
            System.arraycopy(body, 0, bytes, authTokenAndHeaderAndLength.length, bodyLen);

            message.fromByteArray(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            Message message = waitForMessage();
            if (messageReceiveListener != null) messageReceiveListener.onMessageReceived(message);
        }
    }
}
