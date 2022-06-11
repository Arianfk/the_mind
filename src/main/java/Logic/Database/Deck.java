package Logic.Database;

import Logic.Model.Cards.numericalCard;
import Logic.Model.Game.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {

    private LinkedList<String> deckCards;

    private Logic.Model.Game.game game;

    public LinkedList<String> getDeckCards() {
        if (deckCards == null) {
            deckCards = new LinkedList<>();
        }
        return deckCards;
    }

    /**
     * choose few random cards from deck
     * @param t is the number of cards needed from deck
     * @return t numerical card
     */

    public LinkedList<numericalCard> getFromDeck(int t){
        LinkedList<numericalCard> out=new LinkedList<>();
        LinkedList<numericalCard> numericalCardArrayList=numericalCardsDatabase.convertIdToNumericalCards(getDeckCards());
        LinkedList<Integer> randomNumbers=randomNumbers(t);
        for (Integer index : randomNumbers){
            numericalCard card = numericalCardArrayList.get(index);
            out.add(card);
        }
        return out;
    }

    /**
     *
     * @param t is number of wanted randomNumbers
     * @return random number
     */

    public LinkedList<Integer> randomNumbers(int t ){

        LinkedList<Integer> randomNumbers=new LinkedList<>();
        int max=Integer.MIN_VALUE;
        int min=Integer.MAX_VALUE;

        for (int i = 0; i < t; i++) {
            int random = (int) (Math.random()*(max-min+1)+min);
            max=Math.max(random,max);
            min=Math.min(random,min);
            randomNumbers.add(random);
        }

        return randomNumbers;


    }



}
