package Connection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Message {
    public static final int AUTH_TOKEN_LENGTH = 32;
    private byte[] authToken, body;
    private byte header;

    public Message() {
        this.authToken = new byte[AUTH_TOKEN_LENGTH];
        this.body = new byte[0];
        this.header = 0;
    }

    public Message(byte[] authToken) {
        this.authToken = authToken;
        this.body = new byte[0];
        this.header = 0;
    }

    public Message(byte[] authToken, byte header) {
        this.authToken = authToken;
        this.body = new byte[0];
        this.header = header;
    }

    public Message(byte[] authToken, byte header, byte[] body) {
        this.authToken = authToken;
        this.body = body;
        this.header = header;
    }

    public Message(byte[] authToken, byte header, String body) {
        this.authToken = authToken;
        setBody(body);
        this.header = header;
    }

    public Message(byte[] authToken, byte[] body) {
        this.authToken = authToken;
        this.body = body;
        this.header = 0;
    }

    public Message(byte[] authToken, String body) {
        this.authToken = authToken;
        setBody(body);
        this.header = 0;
    }

    public Message(String body) {
        this.authToken = new byte[AUTH_TOKEN_LENGTH];
        setBody(body);
        this.header = 0;
    }

    public Message(String body, byte header) {
        this.authToken = new byte[AUTH_TOKEN_LENGTH];
        setBody(body);
        this.header = header;
    }

    public byte[] getAuthToken() {
        return authToken;
    }

    public void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }

    public byte getHeader() {
        return header;
    }

    public void setHeader(byte header) {
        this.header = header;
    }

    public void setHeader(int header) {
        this.header = (byte) header;
    }

    public byte[] toByteArray() {
        byte[] res = new byte[authToken.length + 1 + 4 + body.length];

        int index = 0;

        System.arraycopy(authToken, 0, res, index, authToken.length);
        index += authToken.length;

        res[index] = header;
        index++;

        byte[] tmp = ByteBuffer.allocate(4).putInt(body.length).array();
        System.arraycopy(tmp, 0, res, index, 4);
        index += 4;

        System.arraycopy(body, 0, res, index, body.length);
        return res;
    }

    public void fromByteArray(byte[] array) {
        int index = 0;

        authToken = new byte[AUTH_TOKEN_LENGTH];
        System.arraycopy(array, index, authToken, 0, AUTH_TOKEN_LENGTH);
        index += AUTH_TOKEN_LENGTH;

        header = array[index];
        index++;

        byte[] tmp = new byte[4];
        System.arraycopy(array, index, tmp, 0, 4);
        int bodyLen = ByteBuffer.wrap(tmp).getInt();
        body = new byte[bodyLen];
        index += 4;

        System.arraycopy(array, index, body, 0, bodyLen);
    }
}