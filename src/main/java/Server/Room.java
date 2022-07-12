package Server;

import Connection.Message;
import Game.Game;
import Game.Player;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private static int ROOM_COUNT = 100000;
    private final SessionHandler host;
    private final int maximumNumberOfPlayers;
    private final String id;
    private final List<SessionHandler> players;
    private final Game game;
    private boolean started = false;

    public Room(SessionHandler host, int maximumNumberOfPlayers) {
        this.id = String.valueOf(ROOM_COUNT++);
        this.host = host;
        this.maximumNumberOfPlayers = maximumNumberOfPlayers;
        this.players = new ArrayList<>();
        this.players.add(host);
        this.game = new Game(maximumNumberOfPlayers);
        Player player = new Player(game);
        game.getPlayers().add(player);

        player.setPlayerHandListener(player1 -> {
            Gson gson = new Gson();
            String json = gson.toJson(player1.getCards());
            host.getConnectionHandler().sendWithAT(new Message(json, (byte) 0x02));

            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                String s = players.get(i).getUserName() + " : " + game.getPlayers().get(i).getCardNumber();
                tmp.add(s);
            }

            json = gson.toJson(tmp);
            for (SessionHandler sessionHandler1 : players) {
                sessionHandler1.getConnectionHandler().sendWithAT(new Message(json, (byte) 0x05));
            }
        });

        this.game.setLastCardListener(lastCard -> {
            for (SessionHandler sessionHandler : players) {
                sessionHandler.getConnectionHandler().sendWithAT(new Message(String.valueOf(lastCard), (byte) 0x01));
            }
            if (game.nextLevelPossible()) players.get(0).getConnectionHandler().sendWithAT(new Message((byte) 0x06));
        });

        this.game.setHeartChangedListener(count -> {
            for (SessionHandler sessionHandler : players) {
                sessionHandler.getConnectionHandler().sendWithAT(new Message(String.valueOf(count), (byte) 0x03));
                if (count == 0) sessionHandler.close();
            }
        });

        this.game.setStarChangedListener(count -> {
            for (SessionHandler sessionHandler : players) {
                sessionHandler.getConnectionHandler().sendWithAT(new Message(String.valueOf(count), (byte) 0x04));
            }
        });

        this.game.setNinjaListener(ninja -> {
            for (int i = 0; i < players.size(); i++) {
                SessionHandler sessionHandler = players.get(i);
                sessionHandler.getConnectionHandler().sendWithAT(new Message((ninja == null ? "" : String.valueOf(ninja[i])), (byte) 0x07));
            }
        });
    }

    public Game getGame() {
        return game;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getId() {
        return id;
    }

    public SessionHandler getHost() {
        return host;
    }

    public int getMaximumNumberOfPlayers() {
        return maximumNumberOfPlayers;
    }

    public List<SessionHandler> getPlayers() {
        return players;
    }

    public void addPlayer(SessionHandler sessionHandler) {
        players.add(sessionHandler);
        Player player = new Player(game);
        game.getPlayers().add(player);
        player.setPlayerHandListener(player1 -> {
            Gson gson = new Gson();
            String json = gson.toJson(player1.getCards());
            sessionHandler.getConnectionHandler().sendWithAT(new Message(json, (byte) 0x02));

            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                String s = players.get(i).getUserName() + " : " + game.getPlayers().get(i).getCardNumber();
                tmp.add(s);
            }

            json = gson.toJson(tmp);
            for (SessionHandler sessionHandler1 : players) {
                sessionHandler1.getConnectionHandler().sendWithAT(new Message(json, (byte) 0x05));
            }
        });
    }

    public void play(SessionHandler sessionHandler) {
        for (int i = 0; i < players.size(); i++) {
            if (sessionHandler == players.get(i)) {
                game.play(game.getPlayers().get(i));
                break;
            }
        }
    }

    public void fillWithBots() {
        int botCount = maximumNumberOfPlayers - players.size();
        for (int i = 0; i < botCount; i++) {
            Bot bot = new Bot("Bot " + (i + 1), id, botCount);
            bot.start();
        }
    }
}
