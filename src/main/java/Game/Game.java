package Game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int[] rewards = {0, 2, 1, 0, 2, 1, 0, 2, 1, 0, 0, 0};
    private final List<Player> players;
    private int level = 0;
    private int starsCount;
    private int lastLevel;
    private int heartsCount;
    private Integer lastPlayedCard = 0;
    private boolean win = false;
    private LastCardListener lastCardListener;
    private HeartChangedListener heartChangedListener;
    private StarChangedListener starChangedListener;
    private NinjaListener ninjaListener;
    private int[] ninjaRes;

    public Game(int playerCount) {
        players = new ArrayList<>();
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

        player.removeMinimumCard();

        boolean flg = false;
        for (Player player1 : players) {
            Integer min1 = player1.getMinimumCard();
            while (min1 != null && min1 < min) {
                flg = true;
                player1.removeMinimumCard();
                min1 = player1.getMinimumCard();
            }
        }

        setLastPlayedCard(min);
        if (flg) {
            setHeartsCount(getHeartsCount() - 1);
        }
    }

    public boolean isWin() {
        return win;
    }

    public void nextLevel() {
        level++;
        if (level > lastLevel) {
            win = true;
            return;
        }

        if (rewards[level - 1] == 1 && heartsCount < 5)
            setHeartsCount(getHeartsCount() + 1);

        if (rewards[level - 1] == 2 && starsCount < 3)
            setStarsCount(getStarsCount() + 1);

        boolean[] marked = new boolean[110];
        //******************************** YASNA ->
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //********************************
        for (Player player : players) {
            while (player.getCardNumber() < level) {
                int x = (int) Math.floor(Math.random() * 100) + 1;
                if (!marked[x]) {
                    marked[x] = true;
                    player.addCard(x);
                }
            }
        }

        setLastPlayedCard(0);
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

    public void setLastPlayedCard(Integer lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
        lastCardListener.onLastCardChanged(lastPlayedCard);
    }

    public int getLevel() {
        return level;
    }

    public void setNinjaResult(Player player, int st) {
        for (int i = 0; i < players.size(); i++) {
            if (player == players.get(i)) {
                ninjaRes[i] = st;
            }
        }

        int flg = 1;
        for (int i = 0; i < players.size(); i++) {
            if (ninjaRes[i] == 0)
                flg = 0;
            else if (ninjaRes[i] == 2 && flg == 1)
                flg = 2;
        }

        if (flg > 0) {
            if (flg == 1) {
                setStarsCount(getStarsCount() - 1);
                Integer max = lastPlayedCard;
                for (Player player1 : players) {
                    Integer min = player1.getMinimumCard();
                    if (min != null)
                        max = Math.max(max, min);
                    player1.removeMinimumCard();
                }

                setLastPlayedCard(max);
            }
            ninjaRes = null;
        }

        ninjaListener.onNinjaChanged(ninjaRes);
    }
}
