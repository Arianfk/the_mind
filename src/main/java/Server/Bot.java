package Server;

import Connection.ConnectionHandler;
import Connection.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Bot extends Thread {
    private final ConnectionHandler connectionHandler;
    private Set<Integer> cards;
    private Integer lastPlayedCard;
    private Thread thread;
    private Gson gson;
    private final int botCount;

    public Bot(String name, String roomId, int botCount) {
        this.botCount = botCount;
        try {
            Socket socket = new Socket("localhost", 80);
            connectionHandler = new ConnectionHandler(socket);
            connectionHandler.setAuthToken(connectionHandler.waitForMessage().getAuthToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        connectionHandler.sendWithAT(new Message(name));
        connectionHandler.sendWithAT(new Message(roomId, (byte) 0x03));
    }

    @Override
    public void run() {
        super.run();

        this.gson = new Gson();
        connectionHandler.setMessageReceiveListener(message -> {
            switch (message.getHeader()) {
                case 0x01 -> {
                    lastPlayedCard = Integer.parseInt(new String(message.getBody()));
                    if (thread != null)
                        thread.interrupt();

                    Runnable runnable = () -> {
                        try {
                            Thread.sleep(getWaitTime());
                            connectionHandler.sendWithAT(new Message((byte) 0x05));
                        } catch (InterruptedException ignored) {

                        }
                    };

                    thread = new Thread(runnable);
                    thread.start();
                }
                case 0x02 -> {
                    String json = new String(message.getBody());
                    Type setType = new TypeToken<HashSet<Integer>>() {
                    }.getType();
                    this.cards = gson.fromJson(json, setType);
                    if (cards.size() > 0)
                        System.out.println(Collections.min(cards));
                }
                case 0x07 -> {
                    if (message.getBody().length == 0)
                        connectionHandler.sendWithAT(new Message("1", (byte) 0x07));
                }
            }
        });
        connectionHandler.start();
    }

    public int getWaitTime() {
        int x = 200;
        if (cards.size() > 0)
            x = (Collections.min(cards) - lastPlayedCard) * 1000;
        int e = (int) Math.floor(Math.random() * 1000);
        return (int) Math.floor((x + e) * 4.0 / (botCount + 3.0));
    }
}
