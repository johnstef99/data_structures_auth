import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class Player {
	private int playerId;
	private String name;
	private int score;
	private Board board;
	private int tile;

	Player() {
		this.playerId = 0;
		this.name = "";
		this.score = 0;
		this.board = new Board();
		this.tile = 0;
	}

	Player(int playerId, String name, int score, Board board) {
		this.playerId = playerId;
		this.name = name;
		this.score = score;
		this.board = board;
		this.tile = 0;
	}

	Player(Player playerObj) {
		this.playerId = playerObj.playerId;
		this.name = playerObj.name;
		this.score = playerObj.score;
		this.board = playerObj.board;
		this.tile = playerObj.tile;
	}

	/**
	 * 
	 * @param id   current tile of the player
	 * @param dice num of dice
	 * @return tileIdAfterMove, numOfSnakesBitten, numOfUsedLadders,
	 *         numOfRedApplesEaten, numOfBlackApplesEaten
	 */
	int[] move(int id, int dice) {

		int tileIdAfterMove = id + dice;
		int numOfSnakesBitten = 0;
		int numOfUsedLadders = 0;
		int numOfRedApplesEaten = 0;
		int numOfBlackApplesEaten = 0;

		List<Integer> snakeHeadsIds = new ArrayList<Integer>();
		for (int i = 0; i != board.getSnakes().length; i++)
			snakeHeadsIds.add(board.getSnakes()[i].getHeadId());

		List<Integer> appleIds = new ArrayList<Integer>();
		for (int i = 0; i != board.getApples().length; i++)
			appleIds.add(board.getApples()[i].getAppleTileId());

		List<Integer> upStepIds = new ArrayList<Integer>();
		for (int i = 0; i != board.getLadders().length; i++)
			upStepIds.add(board.getLadders()[i].getUpStepId());

		boolean somethingHappend = true;

		while (somethingHappend) {
			if (snakeHeadsIds.contains(tileIdAfterMove)) {
				Snake aSnake = board.getSnakes()[snakeHeadsIds.indexOf(tileIdAfterMove)];
				numOfSnakesBitten++;
				tileIdAfterMove = aSnake.getTailId();
				somethingHappend = true;
				System.out.println(this.name + " has been bitten by snake[" + aSnake.getSnakeId() + "]");
			} else
				somethingHappend = false;
			if (appleIds.contains(tileIdAfterMove)) {
				Apple anApple = board.getApples()[appleIds.indexOf(tileIdAfterMove)];
				if (anApple.getColor() == "AR") {
					numOfRedApplesEaten++;
					score += anApple.getPoints();
					anApple.setPoints(0);
				} else {
					numOfBlackApplesEaten++;
					score += anApple.getPoints();
					anApple.setPoints(0);
				}
				System.out.println(this.name + " has eaten apple[" + anApple.getAppleId() + "]");
			}
			if (upStepIds.contains(tileIdAfterMove)) {
				Ladder aLadder = board.getLadders()[upStepIds.indexOf(tileIdAfterMove)];

				if (!aLadder.getBroken()) {
					aLadder.setBroken(true);
					numOfUsedLadders++;
					tileIdAfterMove = aLadder.getDownStepId();
					somethingHappend = true;
					System.out.println(this.name + " has used ladder[" + aLadder.getLadderId() + "]");
				}
			} else
				somethingHappend = false;

		}

		this.tile = tileIdAfterMove;

		return new int[] { tileIdAfterMove, numOfSnakesBitten, numOfUsedLadders, numOfRedApplesEaten,
				numOfBlackApplesEaten };

	}

	/**
	 * getNextMove like the one at MinMaxPlayer and HeuristicPlayer
	 * 
	 * @param id   current tile of the player
	 * @param dice num of dice
	 */
	public int getNextMove(int id) {
		Random random = new Random();
		int dice = random.nextInt(6) + 1;
		return this.move(id, dice)[0];
	};

	public int getPlayerId() {
		return playerId;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public Board getBoard() {
		return board;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return the tile
	 */
	public int getTile() {
		return this.tile;
	}

	/**
	 * @param tile the tile to set
	 */
	public void setTile(int tile) {
		this.tile = tile;
	}
}
