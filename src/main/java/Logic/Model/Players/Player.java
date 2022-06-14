package Logic.Model.Players;

import Logic.Database.numericalCardsDatabase;
import Logic.Model.Cards.numericalCard;

import java.util.LinkedList;
import java.util.Optional;

public class Player {

    public String id;
    public Logic.Model.Game.game game;

    public LinkedList<String> cardsIds=new LinkedList<>();

    public LinkedList<numericalCard> getNumericalCards(){
        return numericalCardsDatabase.convertIdToNumericalCards(cardsIds);
    }

    public LinkedList<String> getNumericalCardsNumber(){
        return numericalCardsDatabase.convertIdToNumericalCardsNumber(cardsIds);
    }

    public Optional<numericalCard> getCardWithMinNumber(){

        LinkedList<numericalCard> numericalCardLinkedList=getNumericalCards();
        return numericalCardLinkedList.stream().min(new numericalCard.Compare());


    }










}
