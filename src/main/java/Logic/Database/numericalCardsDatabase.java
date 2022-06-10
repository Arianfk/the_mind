package Logic.Database;

import Logic.Model.Cards.numericalCard;

import java.util.ArrayList;
import java.util.LinkedList;

public class numericalCardsDatabase {

    private static LinkedList<numericalCard> numericalCards;

    public static LinkedList<numericalCard> getNumericalCards() {
        if (numericalCards==null){
            numericalCards=new LinkedList<>();
        }
        return numericalCards;
    }



    public static numericalCard searchByCardId(String id){

        for (numericalCard numericalCard : getNumericalCards()){
            if (numericalCard.getCardId().equals(id)){
                return numericalCard;
            }
        }
        return null;
    }

    public static LinkedList<numericalCard> convertIdToNumericalCards(LinkedList<String> cardIds){
        LinkedList<numericalCard> out=new LinkedList<>();
        for (numericalCard numericalCard : getNumericalCards()){
            if (cardIds.contains(numericalCard.getCardId())){
                out.add(numericalCard);
            }
        }
        return out;
    }

    public static numericalCard getCardWithMinimumNumber(LinkedList<numericalCard> cards){
        if (cards==null){
            return null;
        }
        int min=101;
        numericalCard cardWithMinNumber=null;
        for (numericalCard  card : cards){
            Integer cardsNumber=card.getCardsNumber();
            if (cardsNumber!=null && cardsNumber<min){
                min=cardsNumber;
                cardWithMinNumber=card;
            }
        }
        return cardWithMinNumber;
    }
}
