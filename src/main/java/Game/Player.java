package Game;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Player {
    private final Game game;
    private Set<Integer> cards;

    public Player(Game game) {
        this.game = game;
        this.cards = new TreeSet<>();
    }

    public Integer getMinimumCard() {
        if (!cards.isEmpty())
            return Collections.min(cards);
        return null;
    }

    public void removeMinimumCard() {
        cards.remove(getMinimumCard());
    }

    public void addCard(Integer card){
        cards.add(card);
    }

    public int getCardNumber() {
        return cards.size();
    }
}
