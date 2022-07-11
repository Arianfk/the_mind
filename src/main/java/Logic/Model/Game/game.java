package Logic.Model.Game;

import Logic.Database.PlayersDatabase;
import Logic.Database.numericalCardsDatabase;
import Logic.Model.Cards.numericalCard;
import Logic.Model.GenerateIds;
import Logic.Model.Players.Player;

import java.util.LinkedList;

public class game {


    public String gameId;
    public LinkedList<String> playersId;
    public Integer level;
    public Integer numberOfNinjaCards;
    public Integer numberOfHeartCards;
    public GenerateIds generateCardIds;
    public String lastPlayedCardId;
    public GenerateIds GeneratePlayerIds;


    public String getLastPlayedCardId() {
        return lastPlayedCardId;
    }

    public String getLastPlayedCardNumber() {
        numericalCard card = numericalCardsDatabase.searchByCardId(lastPlayedCardId);
        if (card == null) {
            return null;
        }
        return card.getCardsNumber() + "";
    }

    public Integer getNumberOfHeartCards() {
        return numberOfHeartCards;
    }

    public Integer getNumberOfNinjaCards() {
        return numberOfNinjaCards;
    }

    public LinkedList<String> getPlayersId() {
        if (playersId == null) {
            playersId = new LinkedList<>();
        }
        return playersId;
    }

    public LinkedList<Player> getPlayers() {
        return PlayersDatabase.convertPlayersIdToPlayers(getPlayersId());
    }

}
