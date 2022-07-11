package Server;

import Game.Game;
import Game.Player;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private static int ROOM_COUNT = 100000;
    private final SessionHandler host;
    private final int maximumNumberOfPlayers;
    private final String id;
    private List<SessionHandler> players;

    private boolean started = false;
    private Game game;

    public Room(SessionHandler host, int maximumNumberOfPlayers) {
        this.id = String.valueOf(ROOM_COUNT++);
        this.host = host;
        this.maximumNumberOfPlayers = maximumNumberOfPlayers;
        this.players = new ArrayList<>();
        this.players.add(host);
        this.game = new Game(maximumNumberOfPlayers);
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

    public void setPlayers(List<SessionHandler> players) {
        this.players = players;
    }

    public void addPlayer(SessionHandler sessionHandler) {
        players.add(sessionHandler);
        game.getPlayers().add(new Player(game));
    }
}
