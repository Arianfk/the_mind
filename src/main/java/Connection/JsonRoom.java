package Connection;

import Server.Room;

public class JsonRoom {
    private String id, hostName;
    private int maximumNumberOfPlayers;

    public JsonRoom(Room room) {
        this.id = room.getId();
        this.hostName = room.getHost().getUserName();
        this.maximumNumberOfPlayers = room.getMaximumNumberOfPlayers();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getMaximumNumberOfPlayers() {
        return maximumNumberOfPlayers;
    }

    public void setMaximumNumberOfPlayers(int maximumNumberOfPlayers) {
        this.maximumNumberOfPlayers = maximumNumberOfPlayers;
    }
}
