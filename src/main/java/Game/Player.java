package Game;

import Game.Listeners.PlayerHandListener;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Player {
    private final Set<Integer> cards;

    public Set<Integer> getCards() {
        return cards;
    }

    private PlayerHandListener playerHandListener;

    public Player(Game game) {
        this.cards = new TreeSet<>();
    }

    public PlayerHandListener getPlayerHandListener() {
        return playerHandListener;
    }

    public void setPlayerHandListener(PlayerHandListener playerHandListener) {
        this.playerHandListener = playerHandListener;
    }

    public Integer getMinimumCard() {
        if (!cards.isEmpty())
            return Collections.min(cards);
        return null;
    }

    public void removeMinimumCard() {
        cards.remove(getMinimumCard());
        playerHandListener.onPlayerHandChanged(this);
    }

    public void addCard(Integer card) {
        cards.add(card);
        playerHandListener.onPlayerHandChanged(this);
    }

    public int getCardNumber() {
        return cards.size();
    }
}
