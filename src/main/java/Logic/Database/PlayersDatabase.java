package Logic.Database;

import Logic.Model.Players.Player;

import java.util.LinkedList;

public class PlayersDatabase {
    public static LinkedList<Player> players;

    public static LinkedList<Player> getPlayers() {
        if (players==null){
            players=new LinkedList<>();
        }
        return players;
    }

    public static LinkedList<Player> convertPlayersIdToPlayers(LinkedList<String> playersId){
        LinkedList<Player> playerLinkedList=new LinkedList<>();
        for (Player x : getPlayers()){
            if (playersId.contains(x.getId())){
                playerLinkedList.add(x);
            }
        }
        return playerLinkedList;
    }
}
