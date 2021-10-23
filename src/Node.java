 import java.util.ArrayList;

class Node {
    private Node parent;
    private ArrayList<Node> children;
    private int nodeDepth;
    private int[] nodeMove;
    private Board nodeBoard;
    private Double nodeEvaluation;

    /**
     * Creates root node
     * @param board
     */
    Node(Board board) {
        this.nodeDepth = 0;
        this.parent = null;
        this.nodeMove = null;
        this.nodeBoard = board;
        this.nodeEvaluation = null;
        this.children = new ArrayList<Node>();
    }

    /**
     * @param parrent
     * @param board
     * @param depth
     * @param tileId
     * @param dice
     * @param evaluation
     */
    Node(Node parrent, Board board, int depth, int tileId, int dice, Double evaluation) {
        this.parent = parrent;
        this.nodeBoard = board;
        this.nodeDepth = depth;
        this.nodeMove = new int[] { tileId, dice };
        this.nodeEvaluation = evaluation;
        this.children = new ArrayList<Node>();
    }

    /**
     * @return the nodeBoard
     */
    public Board getNodeBoard() {
        return nodeBoard;
    }

    /**
     * @return the children
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * @return the nodeDepth
     */
    public int getNodeDepth() {
        return nodeDepth;
    }

    /**
     * @return the nodeEvaluation
     */
    public double getNodeEvaluation() {
        return nodeEvaluation;
    }

    /**
     * @return the nodeMove
     */
    public int[] getNodeMove() {
        return nodeMove;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    /**
     * @param nodeBoard the nodeBoard to set
     */
    public void setNodeBoard(Board nodeBoard) {
        this.nodeBoard = nodeBoard;
    }

    /**
     * @param nodeDepth the nodeDepth to set
     */
    public void setNodeDepth(int nodeDepth) {
        this.nodeDepth = nodeDepth;
    }

    /**
     * @param nodeEvaluation the nodeEvaluation to set
     */
    public void setNodeEvaluation(double nodeEvaluation) {
        this.nodeEvaluation = nodeEvaluation;
    }

    /**
     * @param nodeMove the nodeMove to set
     */
    public void setNodeMove(int[] nodeMove) {
        this.nodeMove = nodeMove;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

}
