package Logic.Model.Players;

import Logic.Database.numericalCardsDatabase;
import Logic.Model.Cards.numericalCard;
import Logic.Model.Game.game;

import java.util.LinkedList;
import java.util.Optional;

public class Player {

    public String id;
    public Logic.Model.Game.game game;

    public LinkedList<String> cardsIds;

    public Logic.Model.Game.game getGame() {
        return game;
    }

    public LinkedList<String> getCardsIds() {
        if (cardsIds==null){
            cardsIds=new LinkedList<>();
        }
        return cardsIds;
    }

    public Integer getNumberOfCards(){
        return getCardsIds().size();
    }
    public LinkedList<numericalCard> getNumericalCards(){
        return numericalCardsDatabase.convertIdToNumericalCards(getCardsIds());
    }

    public LinkedList<String> getNumericalCardsNumber(){
        return numericalCardsDatabase.convertIdToNumericalCardsNumber(getCardsIds());
    }

    public Optional<numericalCard> getCardWithMinNumber(){

        LinkedList<numericalCard> numericalCardLinkedList=getNumericalCards();
        return numericalCardLinkedList.stream().min(new numericalCard.Compare());


    }

    public String getId() {
        return id;
    }
}
