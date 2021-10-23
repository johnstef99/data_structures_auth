public class Snake {
    private int snakeId;
    private int headId;
    private int tailId;

    Snake() {
        this.snakeId = 0;
        this.headId = 0;
        this.tailId = 0;
    }

    Snake(int snakeId, int headId, int tailId) {
        this.snakeId = snakeId;
        this.headId = headId;
        this.tailId = tailId;
    }

    Snake(Snake snakeObj) {
        this.snakeId = snakeObj.snakeId;
        this.tailId = snakeObj.tailId;
        this.headId = snakeObj.headId;
    }

    int getSnakeId() {
        return this.snakeId;
    }

    int getHeadId() {
        return this.headId;
    }

    int getTailId() {
        return this.tailId;
    }

    void setSnakeId(int snakeId) {
        this.snakeId = snakeId;
    }

    void setHeadId(int headId) {
        this.headId = headId;
    }

    void setTailId(int tailId) {
        this.tailId = tailId;
    }
}
