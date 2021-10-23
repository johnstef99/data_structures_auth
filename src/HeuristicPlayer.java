import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class HeuristicPlayer extends Player {

    ArrayList<int[]> path;

    HeuristicPlayer() {
        super();
        this.path = new ArrayList<int[]>();
    }

    HeuristicPlayer(int playerId, String name, int score, Board board) {
        super(playerId, name, score, board);
        this.path = new ArrayList<int[]>();
    }

    HeuristicPlayer(Player player) {
        super(player);
        this.path = new ArrayList<int[]>();
    }

    double evaluate(int currentPos, int dice) {
        int whereAmI = currentPos + dice;
        int points = 0;

        boolean ladderUsed = true;

        while (ladderUsed) {
            // check for snakes
            Snake[] snakes = this.getBoard().getSnakes();
            for (int i = 0; i != snakes.length; i++) {
                Snake snake = snakes[i];
                if (whereAmI == snake.getHeadId()) {
                    whereAmI = snake.getTailId();
                    break;
                }
            }

            // check for Apples
            Apple[] apples = this.getBoard().getApples();
            for (int i = 0; i != apples.length; i++) {
                Apple apple = apples[i];
                if (whereAmI == apple.getAppleTileId()) {
                    points += apple.getPoints();
                    break;
                }
            }

            // check for Ladders
            Ladder[] ladders = this.getBoard().getLadders();
            for (int i = 0; i != ladders.length; i++) {
                Ladder ladder = ladders[i];
                if (whereAmI == ladder.getUpStepId()) {
                    whereAmI = ladder.getDownStepId();
                    ladderUsed = true;
                    break;
                } else {
                    ladderUsed = false;
                }
            }
        }

        return (0.77 * (whereAmI - currentPos) + 0.23 * points);
    }

    public int getNextMove(int currentPos) {
        ArrayList<Move> moves = new ArrayList<Move>();

        for (int dice = 1; dice <= 6; dice++) {
            double evaluation = evaluate(currentPos, dice);
            Move move = new Move(dice, evaluation);
            moves.add(move);
        }

        Collections.sort(moves, new Comparator<Move>() {
            @Override
            public int compare(Move move2, Move move1) {

                return Double.compare(move1.evaluation, move2.evaluation);
            }
        });

        // moves.forEach((move) -> System.out.println(move.toString()));

        Move bestMove = moves.get(0);

        int[] move = this.move(currentPos, bestMove.dice);

        int dice = bestMove.dice;
        int points = 0;
        int numOfRedApples = move[3];
        for (int i = 0; i != numOfRedApples; i++)
            points += 10;
        int numOfBlackApples = move[4];
        for (int i = 0; i != numOfBlackApples; i++)
            points -= 10;
        int steps = move[0] - currentPos;
        int numOfSnakesBitten = move[1];
        int numOfLaddersUsed = move[2];

        int[] details = { dice, points, steps, numOfRedApples, numOfBlackApples, numOfSnakesBitten, numOfLaddersUsed };

        this.path.add(details);

        return move[0];
    }

    void statistics() {
        for (int i = 0; i != this.path.size(); i++) {
            int[] roundDetails = path.get(i);
            int numOfSnakesBitten = roundDetails[5];
            int numOfLaddersUsed = roundDetails[6];
            int numOfRedApples = roundDetails[3];
            int numOfBlackApples = roundDetails[4];
            System.out.print(this.getName() + " ");
            System.out.println("on round " + (i + 1) + " player has bitten by " + numOfSnakesBitten + " snakes, used "
                    + numOfLaddersUsed + " ladders and eaten " + numOfRedApples + " red apples and " + numOfBlackApples
                    + " black apples");

        }
    }

}

class Move {
    int dice;
    double evaluation;

    Move(int dice, double evaluation) {
        this.dice = dice;
        this.evaluation = evaluation;
    };

    @Override
    public String toString() {
        return "Dice: " + this.dice + " Evaluation: " + this.evaluation;
    }
}
