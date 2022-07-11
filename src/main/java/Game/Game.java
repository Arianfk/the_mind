package Game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int[] rewards = {0, 2, 1, 0, 2, 1, 0, 2, 1, 0, 0, 0};
    private final int playerCount;
    private final List<Player> players;
    private int level = 0;
    private int starsCount;
    private int lastLevel;
    private int heartsCount;
    private Integer lastPlayedCard = 0;
    private boolean lost = false;
    private boolean finished = false;

    public Game(int playerCount) {
        players = new ArrayList<>();
        this.playerCount = playerCount;
        this.starsCount = 1;
        switch (playerCount) {
            case 2 -> {
                this.lastLevel = 12;
                this.heartsCount = 2;
            }
            case 3 -> {
                this.lastLevel = 10;
                this.heartsCount = 3;
            }
            case 4 -> {
                this.lastLevel = 8;
                this.heartsCount = 4;
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void play(Player player) {
        Integer min = player.getMinimumCard();
        if (min == null)
            return;

        lastPlayedCard = min;
        player.removeMinimumCard();
        boolean flg = false;
        for (Player player1 : players) {
            Integer min1 = player1.getMinimumCard();
            if (min1 != null && min1 < min) {
                flg = true;
                player1.removeMinimumCard();
            }
        }

        if (flg) {
            heartsCount--;
            if (heartsCount == 0) {
                lost = true;
            }
        }
    }

    public void nextLevel() {
        level++;
        if (level > lastLevel) {
            finished = true;
            return;
        }

        if (rewards[level - 1] == 1 && heartsCount < 5)
            heartsCount++;

        if (rewards[level - 1] == 2 && starsCount < 3)
            starsCount++;

        boolean[] marked = new boolean[110];
        for (Player player : players) {
            while (player.getCardNumber() < level) {
                int x = (int) Math.floor(Math.random() * 100) + 1;
                if (!marked[x]) {
                    marked[x] = true;
                    player.addCard(x);
                }
            }
        }
    }
}
