package Logic.Model.Cards;

import Logic.Model.Game.game;

import java.util.Comparator;

enum position {
    inHand, deck
}

public class numericalCard {


    private final Integer cardsNumber;
    private final Logic.Model.Game.game game;
    private position cardPosition;
    private String cardId;


    public numericalCard(game Game, Integer cardsNumber) {

        this.game = Game;
        this.cardsNumber = cardsNumber;
        cardId = game.generateCardIds.generateId();
        cardPosition = position.deck;


    }


    public numericalCard(int cardsNumber, Logic.Model.Game.game game) {
        this.game = game;
        this.cardPosition = position.deck;
        this.cardsNumber = cardsNumber;
    }

    public String getCardId() {
        return cardId;
    }

    public Integer getCardsNumber() {
        return cardsNumber;
    }

    public position getCardPosition() {
        return cardPosition;
    }

    public static class Compare implements Comparator<numericalCard> {

        @Override
        public int compare(numericalCard o1, numericalCard o2) {
            String aColumn = o1.getCardsNumber() + "";
            String bColumn = o2.getCardsNumber() + "";
            return aColumn.compareTo(bColumn);
        }
    }


}
