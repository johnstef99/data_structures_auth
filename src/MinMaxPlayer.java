 import java.util.ArrayList;

class MinMaxPlayer extends Player {

    ArrayList<int[]> path;

    MinMaxPlayer(int playerId, String name, int score, Board board) {
        super(playerId, name, score, board);
        this.path = new ArrayList<int[]>();
    }

    MinMaxPlayer(Player player) {
        super(player);
        this.path = new ArrayList<int[]>();
    }

    double evaluate(int currentPos, int dice, Board board) {
        int whereAmI = currentPos + dice;
        int points = 0;

        boolean ladderUsed = true;

        while (ladderUsed) {
            // check for snakes
            Snake[] snakes = board.getSnakes();
            for (int i = 0; i != snakes.length; i++) {
                Snake snake = snakes[i];
                if (whereAmI == snake.getHeadId()) {
                    whereAmI = snake.getTailId();
                    break;
                }
            }

            // check for Apples
            Apple[] apples = board.getApples();
            for (int i = 0; i != apples.length; i++) {
                Apple apple = apples[i];
                if (whereAmI == apple.getAppleTileId()) {
                    points += apple.getPoints();
                    break;
                }
            }

            // check for Ladders
            Ladder[] ladders = board.getLadders();
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

    public int getNextMove(int currentPos, int opponentCurrentPos) {
        Node rootNode = new Node(this.getBoard());
        createMySubtree(rootNode, 1, currentPos, opponentCurrentPos);
        int pointerOfBestMove = chooseMinMax(rootNode);
        int[] nodeMove = rootNode.getChildren().get(pointerOfBestMove).getNodeMove();

        int[] move = this.move(nodeMove[0], nodeMove[1]);

        int dice = nodeMove[1];
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

    /**
     * 
     * @param parent
     * @param depth
     * @param currentPos
     * @param opponentCurrentPos
     */
    void createMySubtree(Node parent, int depth, int currentPos, int opponentCurrentPos) {
        int numOfAvailableMoves = 6;
        Board currentBoard = parent.getNodeBoard();
        int boardSize = currentBoard.getM() * currentBoard.getN();
        if (currentPos + 6 > boardSize)
            numOfAvailableMoves = boardSize - currentPos;
        for (int dice = 1; dice <= numOfAvailableMoves; dice++) {
            Board newBoard = simulateMove(currentPos, dice, currentBoard);
            double moveEvaluation = evaluate(currentPos, dice, currentBoard);
            Node child = new Node(parent, newBoard, depth, currentPos, dice, null);
            parent.getChildren().add(child);
            createOppenentSubtree(child, depth + 1, currentPos + dice, opponentCurrentPos, moveEvaluation);
        }
        }

    /**
     * 
     * @param parent
     * @param depth
     * @param currentPos
     * @param opponentCurrentPosl
     */
    void createOppenentSubtree(Node parent, int depth, int currentPos, int opponentCurrentPosl, double parrentEval) {
        int numOfAvailableMoves = 6;
        Board currentBoard = parent.getNodeBoard();
        int boardSize = currentBoard.getM() * currentBoard.getN();
        if (opponentCurrentPosl + 6 > boardSize)
            numOfAvailableMoves = boardSize - opponentCurrentPosl;
        for (int dice = 1; dice <= numOfAvailableMoves; dice++) {
            Board newBoard = simulateMove(opponentCurrentPosl, dice, currentBoard);
            double moveEvaluation = parrentEval - evaluate(opponentCurrentPosl, dice, currentBoard);
            Node child = new Node(parent, newBoard, depth, currentPos, dice, moveEvaluation);
            parent.getChildren().add(child);
        }
    }

    /**
     * 
     * @param id
     * @param dice
     * @param board
     * @return new Board with the move simulated
     */
    Board simulateMove(int id, int dice, Board board) {
        int tileIdAfterMove = id + dice;
        Board newBoard = new Board(board);

        ArrayList<Integer> snakeHeadsIds = new ArrayList<Integer>();
        for (int i = 0; i != newBoard.getSnakes().length; i++)
            snakeHeadsIds.add(newBoard.getSnakes()[i].getHeadId());

        ArrayList<Integer> appleIds = new ArrayList<Integer>();
        for (int i = 0; i != newBoard.getApples().length; i++)
            appleIds.add(newBoard.getApples()[i].getAppleTileId());

        ArrayList<Integer> upStepIds = new ArrayList<Integer>();
        for (int i = 0; i != newBoard.getLadders().length; i++)
            upStepIds.add(newBoard.getLadders()[i].getUpStepId());

        boolean somethingHappend = true;

        while (somethingHappend) {
            if (snakeHeadsIds.contains(tileIdAfterMove)) {
                Snake aSnake = newBoard.getSnakes()[snakeHeadsIds.indexOf(tileIdAfterMove)];
                tileIdAfterMove = aSnake.getTailId();
                somethingHappend = true;
            } else
                somethingHappend = false;
            if (appleIds.contains(tileIdAfterMove)) {
                Apple anApple = newBoard.getApples()[appleIds.indexOf(tileIdAfterMove)];
                if (anApple.getColor() == "AR") {
                    anApple.setPoints(0);
                } else {
                    anApple.setPoints(0);
                }
            }
            if (upStepIds.contains(tileIdAfterMove)) {
                Ladder aLadder = newBoard.getLadders()[upStepIds.indexOf(tileIdAfterMove)];

                if (!aLadder.getBroken()) {
                    tileIdAfterMove = aLadder.getDownStepId();
                    aLadder.setBroken(true);
                    somethingHappend = true;
                }

            } else
                somethingHappend = false;

        }
        return newBoard;
    }

    /**
     * @param root Node
     * @return the pointer of the best move
     */
    int chooseMinMax(Node root) {

        for (int i = 0; i != root.getChildren().size(); i++) {
            Node aNode = root.getChildren().get(i);
            double min = aNode.getChildren().get(0).getNodeEvaluation();
            for (int j = 1; j != aNode.getChildren().size(); j++) {
                if (aNode.getChildren().get(j).getNodeEvaluation() < min)
                    min = aNode.getChildren().get(j).getNodeEvaluation();
            }
            root.getChildren().get(i).setNodeEvaluation(min);
        }

        int pointer = 0;
        double max = root.getChildren().get(pointer).getNodeEvaluation();
        for (int i = 1; i != root.getChildren().size(); i++) {
            if (root.getChildren().get(i).getNodeEvaluation() >= max) {
                pointer = i;
                max = root.getChildren().get(i).getNodeEvaluation();
            }
        }
        root.setNodeEvaluation(max);

        return pointer;

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
