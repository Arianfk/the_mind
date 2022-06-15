package Logic.Controller;

import Logic.Model.Game.game;
import Logic.Model.Players.Player;

import java.util.ArrayList;

public class GameCardsInformation {

    public game currentGame;


    public GameCardsInformation(game game){
        this.currentGame=game;
    }

    public ArrayList<String> getGameCardsInformation(){
        ArrayList<String> info=new ArrayList<>();
        for (Player x : currentGame.getPlayers()){
            String playersId=x.getId();
            String numberOdRemainedCards=x.getNumberOfCards()+"";
            String message="player with id "+playersId+" have "+numberOdRemainedCards+" cards";
            info.add(message);
        }
        return info;
    }




}
