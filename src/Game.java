public class Game {
    int round;

    Game() {
        this.round = 0;
    }

    Game(int round) {
        this.round = round;
    }

    Game(Game aGame) {
        this.round = aGame.round;
    }

    /**
     * @return the round
     */
    public int getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(int round) {
        this.round = round;
    }

}
