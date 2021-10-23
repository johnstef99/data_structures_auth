import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private int n, m;
    private int[][] tiles;
    private Snake[] snakes;
    private Ladder[] ladders;
    private Apple[] apples;

    Board() {
        this.m = 0;
        this.n = 0;
        this.tiles = new int[n][m];
        this.snakes = new Snake[0];
        this.ladders = new Ladder[0];
        this.apples = new Apple[0];
    }

    Board(int n, int m, int numOfSnakes, int numOfLadders, int numOfApples) {
        this.m = m;
        this.n = n;
        this.tiles = new int[n][m];
        this.snakes = new Snake[numOfSnakes];
        this.ladders = new Ladder[numOfLadders];
        this.apples = new Apple[numOfApples];
    }

    Board(Board boardObj) {
        this.n = boardObj.n;
        this.m = boardObj.m;
        this.tiles = boardObj.tiles;
        this.snakes = boardObj.snakes;
        this.ladders = new Ladder[boardObj.ladders.length];
        for (int i = 0; i != boardObj.ladders.length; i++) {
            this.ladders[i] = new Ladder(boardObj.ladders[i]);
        }
        this.apples = new Apple[boardObj.apples.length];
        for (int i = 0; i != boardObj.apples.length; i++) {
            this.apples[i] = new Apple(boardObj.apples[i]);
        }
    }

    void createBoard() {
        nameTiles();
        fillSnakes(snakes.length);
        fillLadders(ladders.length);
        fillApples(apples.length);
    }

    void createElementBoard() {
        String[][] elementBoardSnakes = elementBoardSnakes();
        print(elementBoardSnakes);
        System.out.print("\n\n");

        String[][] elementBoardLadders = elementBoardLadders();
        print(elementBoardLadders);
        System.out.print("\n\n");

        String[][] elementBoardApples = elementBoardApples();
        print(elementBoardApples);
        System.out.print("\n\n");
    }

    public void printTiles() {
        for (int n = 0; n != this.n; n++) {
            for (int m = 0; m != this.m; m++) {
                System.out.print(tiles[n][m]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void print(String[][] elementBoard) {
        for (int n = 0; n != this.n; n++) {
            for (int m = 0; m != this.m; m++) {
                System.out.print(elementBoard[n][m] + "  ");
            }
            System.out.println();
        }
    }

    String[][] elementBoardSnakes() {
        String[][] output = new String[this.n][this.m];

        boolean snakeExist = true;
        for (int n = 0; n != this.n; n++) {
            for (int m = 0; m != this.m; m++) {
                for (int SnakeIndex = 0; SnakeIndex != snakes.length; SnakeIndex++) {
                    if (tiles[n][m] == snakes[SnakeIndex].getHeadId()) {
                        // System.out.print("SH" + snakes[SnakeIndex].getSnakeId());
                        output[n][m] = "SH" + snakes[SnakeIndex].getSnakeId();
                        snakeExist = true;
                        break;
                    } else if (tiles[n][m] == snakes[SnakeIndex].getTailId()) {
                        // System.out.print("ST" + snakes[SnakeIndex].getSnakeId());
                        output[n][m] = "ST" + snakes[SnakeIndex].getSnakeId();
                        snakeExist = true;
                        break;
                    } else
                        snakeExist = false;
                }
                if (!snakeExist)
                    output[n][m] = "___";
                // System.out.print(" ");
                snakeExist = true;
            }
            // System.out.println();
        }

        return output;

    }

    String[][] elementBoardLadders() {
        String[][] output = new String[this.n][this.m];

        boolean snakeExist = true;
        for (int n = 0; n != this.n; n++) {
            for (int m = 0; m != this.m; m++) {
                for (int LadderIndex = 0; LadderIndex != ladders.length; LadderIndex++) {
                    if (tiles[n][m] == ladders[LadderIndex].getDownStepId()) {
                        output[n][m] = "LU" + ladders[LadderIndex].getLadderId();
                        snakeExist = true;
                        break;
                    } else if (tiles[n][m] == ladders[LadderIndex].getUpStepId()) {
                        output[n][m] = "LD" + ladders[LadderIndex].getLadderId();
                        snakeExist = true;
                        break;
                    } else
                        snakeExist = false;
                }
                if (!snakeExist)
                    output[n][m] = "___";
                snakeExist = true;
            }

        }

        return output;

    }

    String[][] elementBoardApples() {
        String[][] output = new String[n][m];

        boolean appleExist = true;
        for (int n = 0; n != this.n; n++) {
            for (int m = 0; m != this.m; m++) {
                for (int appleIndex = 0; appleIndex != apples.length; appleIndex++) {
                    if (tiles[n][m] == apples[appleIndex].getAppleTileId()) {
                        output[n][m] = apples[appleIndex].getColor() + apples[appleIndex].getAppleId();
                        appleExist = true;
                        break;
                    } else
                        appleExist = false;
                }
                if (!appleExist)
                    output[n][m] = "___";
                appleExist = true;

            }

        }

        return output;
    }

    /**
     * Get the row num of a tile
     * 
     * @param tile
     * @return the row of the tile
     */
    private int rowFromTile(int tile) {
        if (tile % m == 0)
            return tile / m;
        else
            return (tile / m) + 1;
    }

    /**
     * Fill the board with Snakes.
     * 
     * @param numofSnakes the number of snakes to create
     */
    private void fillSnakes(int numOfSnakes) {
        // creating a list with (N*M)-1 tiles
        List<Integer> availableTiles = new ArrayList<Integer>();
        for (int i = 0; i + 1 != m * n; i++)
            availableTiles.add(i + 1);

        Random random = new Random();

        int startForHead = m + 1; // the min tile for a snake head
        int maxTailNum;
        for (int i = 0; i != numOfSnakes; i++) {
            // get random head

            // startForHead is used and it is no longer part of the availableTiles list set
            // min tile to the next tile
            while (availableTiles.indexOf(startForHead) == -1) {
                startForHead++;
            }

            int randomHeadIndex = random.nextInt(availableTiles.size() - availableTiles.indexOf(startForHead))
                    + availableTiles.indexOf(startForHead);
            int headId = availableTiles.get(randomHeadIndex);

            // get random tail
            maxTailNum = m * (rowFromTile(headId) - 1);

            // if the tile of maxTailNum is already taken search below
            while (availableTiles.indexOf(maxTailNum) == -1) {
                maxTailNum--;
                // if you hit ZERO it means there is no tile for the tail so select an other
                // head
                if (maxTailNum == 0) {
                    randomHeadIndex++;
                    headId = availableTiles.get(randomHeadIndex);
                    maxTailNum = m * (rowFromTile(headId) - 1);
                }
            }

            // remove the final head
            availableTiles.remove(randomHeadIndex);

            int randomTailIndex;
            // if index of maxTailNum is 0 don't try to create a random num from 0 to 0
            if (availableTiles.indexOf(maxTailNum) == 0)
                randomTailIndex = 0;
            else
                randomTailIndex = random.nextInt(availableTiles.indexOf(maxTailNum));

            // remove selected tail
            int tailId = availableTiles.remove(randomTailIndex);

            snakes[i] = new Snake(i, headId, tailId);
        }
    }

    /**
     * Fille board with ladders
     * 
     * @param numOfLadders
     */
    private void fillLadders(int numOfLadders) {
        // list with (n*m)-1 tiles
        List<Integer> availableTiles = new ArrayList<Integer>();
        for (int i = 0; i + 1 != m * n; i++)
            availableTiles.add(i + 1);

        // List with the tiles that have snake's heads
        List<Integer> snakeHeads = new ArrayList<Integer>();
        for (int i = 0; i != snakes.length; i++) {
            snakeHeads.add(snakes[i].getHeadId());
        }

        Random random = new Random();

        int startForDownStep = m + 1; // the min tile that you can place downStep
        int maxUpStepNum;
        for (int i = 0; i != numOfLadders; i++) {
            // get random DownStep

            while (availableTiles.indexOf(startForDownStep) == -1) {
                startForDownStep++;
            }
            int randomStepDownIndex = random.nextInt(availableTiles.size() - availableTiles.indexOf(startForDownStep))
                    + availableTiles.indexOf(startForDownStep);
            int downStepId = availableTiles.get(randomStepDownIndex);

            // get random stepUp
            maxUpStepNum = m * (rowFromTile(downStepId) - 1);

            // if the tile of stepUp is already taken search below
            while (availableTiles.indexOf(maxUpStepNum) == -1) {
                maxUpStepNum--;
                // if you hit ZERO it means there is no tile for the tail so select an other
                // head
                if (maxUpStepNum == 0) {
                    randomStepDownIndex++;
                    downStepId = availableTiles.get(randomStepDownIndex);
                    maxUpStepNum = m * (rowFromTile(downStepId) - 1);
                }
            }

            // remove the final head
            availableTiles.remove(randomStepDownIndex);

            int randomStepUpIndex = -1;
            boolean stepUpOnHead = true;
            // if the maxTailNum is the last tail you can take dont try to create a random
            // num from ZERO to ZERO
            while (stepUpOnHead) {
                if (availableTiles.indexOf(maxUpStepNum) == 0)
                    randomStepUpIndex = 0;
                else
                    randomStepUpIndex = random.nextInt(availableTiles.indexOf(maxUpStepNum));
                if (!snakeHeads.contains(availableTiles.get(randomStepUpIndex)))
                    stepUpOnHead = false;
            }

            int upStepId = availableTiles.remove(randomStepUpIndex);

            ladders[i] = new Ladder(i, upStepId, downStepId, false);
        }
    }

    /**
     * Fill board with apples
     * 
     * @param numOfApples
     */
    private void fillApples(int numOfApples) {
        // creating list with all the tiles exept those with snake's head
        List<Integer> availableTiles = new ArrayList<Integer>();
        for (int i = 0; i != n * m; i++)
            availableTiles.add(i + 1);
        for (int i = 0; i != snakes.length; i++)
            availableTiles.remove(availableTiles.indexOf(snakes[i].getHeadId()));

        Random random = new Random();

        for (int i = 0; i != numOfApples; i++) {
            int randomAppleIndex = random.nextInt(availableTiles.size() - 1);
            int randomAppleId = availableTiles.remove(randomAppleIndex);

            boolean isRed = random.nextBoolean();
            int points;
            String color;
            if (isRed) {
                points = 10;
                color = "AR";
            } else {
                points = -10;
                color = "AB";
            }

            apples[i] = new Apple(i, randomAppleId, color, points);

        }

    }

    /**
     * Set every tile[n][m] to a number.
     */
    private void nameTiles() {
        boolean anapoda = false;
        int i = 1;

        for (int n = this.n; n != 0; n--) {
            if (!anapoda) {
                for (int m = 0; m != this.m; m++) {
                    tiles[n - 1][m] = i;
                    i++;
                }
            } else {
                for (int m = this.m; m != 0; m--) {
                    tiles[n - 1][m - 1] = i;
                    i++;
                }
            }
            anapoda = !anapoda;
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Apple[] getApples() {
        return apples;
    }

    public Ladder[] getLadders() {
        return ladders;
    }

    /**
     * @return the snakes
     */
    public Snake[] getSnakes() {
        return snakes;
    }

    public int[][] getTiles() {
        return tiles;
    }

    /**
     * @param apples the apples to set
     */
    public void setApples(Apple[] apples) {
        this.apples = apples;
    }

    /**
     * @param ladders the ladders to set
     */
    public void setLadders(Ladder[] ladders) {
        this.ladders = ladders;
    }

    /**
     * @param m the m to set
     */
    public void setM(int m) {
        this.m = m;
    }

    /**
     * @param n the n to set
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * @param snakes the snakes to set
     */
    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }

    /**
     * @param tiles the tiles to set
     */
    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }
}
