public class Apple {
	private int appleId;
	private int appleTileId;
	private String color;
	private int points;

	Apple() {
		this.appleId = 0;
		this.appleTileId = 0;
		this.color = "black";
		this.points = 0;
	}

	Apple(int appleId, int appleTileId, String color, int points) {
		this.appleId = appleId;
		this.appleTileId = appleTileId;
		this.color = color;
		this.points = points;
	}

	Apple(Apple appleObj) {
		this.appleId = appleObj.appleId;
		this.appleTileId = appleObj.appleTileId;
		this.color = appleObj.color;
		this.points = appleObj.points;
	}

	/**
	 * @return the appleId
	 */
	public int getAppleId() {
		return appleId;
	}

	/**
	 * @return the appleTileId
	 */
	public int getAppleTileId() {
		return appleTileId;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param appleId the appleId to set
	 */
	public void setAppleId(int appleId) {
		this.appleId = appleId;
	}

	/**
	 * @param appleTileId the appleTileId to set
	 */
	public void setAppleTileId(int appleTileId) {
		this.appleTileId = appleTileId;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
}
