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
    private LastCardListener lastCardListener;
    private HeartChangedListener heartChangedListener;
    private StarChangedListener starChangedListener;
    private NinjaListener ninjaListener;
    private int[] ninjaRes;
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

    public void setNinjaListener(NinjaListener ninjaListener) {
        this.ninjaListener = ninjaListener;
    }

    public void setHeartChangedListener(HeartChangedListener heartChangedListener) {
        this.heartChangedListener = heartChangedListener;
    }

    public void setStarChangedListener(StarChangedListener starChangedListener) {
        this.starChangedListener = starChangedListener;
    }

    public LastCardListener getLastCardListener() {
        return lastCardListener;
    }

    public void setLastCardListener(LastCardListener lastCardListener) {
        this.lastCardListener = lastCardListener;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
        starChangedListener.starChanged(starsCount);
    }

    public int getHeartsCount() {
        return heartsCount;
    }

    public void setHeartsCount(int heartsCount) {
        this.heartsCount = heartsCount;
        heartChangedListener.onHeartChanged(heartsCount);
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
        lastCardListener.onLastCardChanged(lastPlayedCard);
        boolean flg = false;
        for (Player player1 : players) {
            Integer min1 = player1.getMinimumCard();
            if (min1 != null && min1 < min) {
                flg = true;
                player1.removeMinimumCard();
            }
        }

        if (flg) {
            setHeartsCount(getHeartsCount() - 1);
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
            setHeartsCount(getHeartsCount() + 1);

        if (rewards[level - 1] == 2 && starsCount < 3)
            setStarsCount(getStarsCount() + 1);

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

        heartChangedListener.onHeartChanged(getHeartsCount());
        starChangedListener.starChanged(getStarsCount());
    }

    public boolean nextLevelPossible() {
        for (Player player : players) {
            if (player.getCardNumber() > 0) {
                return false;
            }
        }

        return true;
    }

    public void newNinjaReq(Player player) {
        ninjaRes = new int[players.size()];
        for (int i = 0; i < players.size(); i++) {
            Player player1 = players.get(i);
            if (player == player1) {
                ninjaRes[i] = 1;
                break;
            }
        }

        ninjaListener.onNinjaChanged(ninjaRes);
    }

    public void setNinjaResult(Player player, int st) {
        for (int i = 0; i < players.size(); i++) {
            if (player == players.get(i)) {
                ninjaRes[i] = st;
            }
        }

        ninjaListener.onNinjaChanged(ninjaRes);
    }
}
