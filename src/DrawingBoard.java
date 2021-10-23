import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class DrawingBoard extends JComponent {
    Board board;
    int size = 50;
    Rectangle[] tilesRect;
    int paddingX = 5;
    int paddingY = 5;
    Graphics2D g2;
    ArrayList<Player> players;

    DrawingBoard(Board board, ArrayList<Player> players) {
        this.players = players;
        this.board = board;
        this.tilesRect = new Rectangle[board.getM() * board.getN()];
        setPreferredSize(new Dimension((int) this.getBounds().getMaxX(), (int) this.getBounds().getMaxY()));
    }

    @Override
    public void paint(Graphics g) {
        this.g2 = (Graphics2D) g;
        fillTiles(g2);
        fillSnakes(g2);
        fillLadders(g2);
        fillApples(g2);
        fillPlayer(g2);
    }

    void fillPlayer(Graphics2D g2) {
        for (int i = 0; i != players.size(); i++) {
            if (players.get(i).getTile() != 0) {
                Ellipse2D.Double playerUI = new Ellipse2D.Double(tilesRect[players.get(i).getTile() - 1].getX() + 5 + 20*i,
                        tilesRect[players.get(i).getTile() - 1].getY() + 35, 10, 10);
                if(i==1)g2.setColor(Color.MAGENTA);
                else g2.setColor(Color.cyan);
                g2.draw(playerUI);
                g2.fill(playerUI);
            }
        }
    }

    void fillTiles(Graphics2D g2) {
        for (int y = 0; y != this.board.getN(); y++) {
            for (int x = 0; x != this.board.getM(); x++) {
                Rectangle rectangle = new Rectangle(paddingX + (x * size), paddingY + (y * size), size, size);
                this.tilesRect[this.board.getTiles()[y][x] - 1] = rectangle;
                g2.draw(rectangle);
                g2.drawString(Integer.toString(this.board.getTiles()[y][x]),
                        Float.parseFloat(Double.toString(rectangle.getCenterX())),
                        Float.parseFloat(Double.toString(rectangle.getCenterY())));

            }
        }
    }

    void fillSnakes(Graphics2D g2) {
        Snake[] snakes = this.board.getSnakes();
        g2.setColor(Color.yellow);
        for (int i = 0; i != snakes.length; i++) {
            Point2D.Double head = new Point2D.Double(this.tilesRect[snakes[i].getHeadId() - 1].getCenterX(),
                    this.tilesRect[snakes[i].getHeadId()].getCenterY());
            Point2D.Double tail = new Point2D.Double(this.tilesRect[snakes[i].getTailId() - 1].getCenterX(),
                    this.tilesRect[snakes[i].getTailId()].getCenterY());
            Line2D.Double snakeLine = new Line2D.Double(head, tail);
            g2.setStroke(new BasicStroke(2 + i));
            g2.draw(snakeLine);
        }
    }

    void fillLadders(Graphics2D g2) {
        Ladder[] ladders = this.board.getLadders();
        g2.setColor(Color.green);
        for (int i = 0; i != ladders.length; i++) {
            Point2D.Double upStep = new Point2D.Double(this.tilesRect[ladders[i].getUpStepId() - 1].getCenterX(),
                    this.tilesRect[ladders[i].getUpStepId()].getCenterY());
            Point2D.Double downStep = new Point2D.Double(this.tilesRect[ladders[i].getDownStepId() - 1].getCenterX(),
                    this.tilesRect[ladders[i].getDownStepId()].getCenterY());
            Line2D.Double snakeLine = new Line2D.Double(upStep, downStep);
            g2.setStroke(new BasicStroke(2 + i));
            if (!ladders[i].getBroken())
                g2.draw(snakeLine);
        }
    }

    void fillApples(Graphics2D g2) {
        Apple[] apples = this.board.getApples();
        for (int i = 0; i != apples.length; i++) {
            Ellipse2D.Double apple = new Ellipse2D.Double(this.tilesRect[apples[i].getAppleTileId() - 1].getX() + 5,
                    this.tilesRect[apples[i].getAppleTileId() - 1].getY() + 5, 10, 10);
            if (apples[i].getPoints() > 0) {
                g2.setColor(Color.RED);
                g2.draw(apple);
                g2.fill(apple);
            } else if (apples[i].getPoints() < 0) {
                g2.setColor(Color.BLACK);
                g2.draw(apple);
                g2.fill(apple);
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(paddingX, paddingY, size * board.getN() + paddingX, size * board.getM() + paddingY);
    }

}
