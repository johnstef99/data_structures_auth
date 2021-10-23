import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class UI {
    // to check authenticity test with a non-square board
    static Board aBoard = new Board(10, 10, 3, 3, 5);;
    static DrawingBoard dBoard;
    static Game game = new Game();

    // playerA
    static Player playerA;
    static JComboBox<String> playerAType = new JComboBox<String>();
    static JLabel playerAScore = new JLabel();
    static JLabel playerAName = new JLabel();

    // playerB
    static Player playerB;
    static JComboBox<String> playerBType = new JComboBox<String>();
    static JLabel playerBScore = new JLabel();
    static JLabel playerBName = new JLabel();

    static ArrayList<Player> players = new ArrayList<Player>();

    static JFrame window;
    static JPanel bottonPanel;
    static JPanel leftPanel;
    static JPanel rightPanel;
    static JPanel topPanel;
    static JLabel roundLabel;

    public static void main(String[] args) {

        window = createWindow();
        roundLabel = new JLabel();

        // creating game
        newGame();

        bottonPanel = buldBottomPanel();

        topPanel = new JPanel();

        topPanel.add(roundLabel, BorderLayout.WEST);

        playerAName.setForeground(Color.blue);
        playerBName.setForeground(Color.MAGENTA);
        leftPanel = buildPlayerPanel(dBoard, players.get(0), playerAName, playerAScore, playerAType);
        rightPanel = buildPlayerPanel(dBoard, players.get(1), playerBName, playerBScore, playerBType);

        // window
        window.add(bottonPanel, BorderLayout.SOUTH);
        window.add(leftPanel, BorderLayout.WEST);
        window.add(rightPanel, BorderLayout.EAST);
        window.add(dBoard, BorderLayout.CENTER);
        window.add(topPanel, BorderLayout.NORTH);
        window.pack();
        window.setVisible(true);
    }

    static JFrame createWindow() {
        JFrame window = new JFrame();
        window.setSize(640, 640);
        window.setTitle("Original Snake D");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        return window;
    }

    private static JPanel buldBottomPanel() {
        JPanel bottomPanel = new JPanel();

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean thereIsAWinner = false;
                // move Players
                for (int i = 0; i != players.size(); i++) {
                    if (players.get(i) instanceof MinMaxPlayer) {
                        MinMaxPlayer minMaxPlayer = (MinMaxPlayer) players.get(i);
                        int opponentIndex = 0;
                        if (i == 0)
                            opponentIndex = 1;
                        minMaxPlayer.getNextMove(minMaxPlayer.getTile(), players.get(opponentIndex).getTile());
                        players.set(i, minMaxPlayer);
                    } else if (players.get(i) instanceof HeuristicPlayer) {
                        HeuristicPlayer heuristicPlayer = (HeuristicPlayer) players.get(i);
                        heuristicPlayer.getNextMove(heuristicPlayer.getTile());
                        players.set(i, heuristicPlayer);
                    } else {
                        players.get(i).getNextMove(players.get(i).getTile());
                    }
                    if (checkForWinner(players.get(i))) {
                        JFrame winnerFrame = new JFrame("Winner");
                        JLabel winnerJLabel = new JLabel();
                        winnerJLabel.setText("Winner is " + players.get(i).getName() + "!!! New game created.");
                        JPanel panel = new JPanel();
                        JButton closeButton = new JButton("Close");
                        closeButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                winnerFrame.setVisible(false);
                            }
                        });
                        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        winnerJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        panel.add(winnerJLabel);
                        panel.add(closeButton);
                        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                        winnerFrame.add(panel);
                        winnerFrame.pack();
                        winnerFrame.setResizable(false);
                        winnerFrame.setVisible(true);
                        thereIsAWinner = true;
                        break;
                    }
                }

                if (!thereIsAWinner) {// add round
                    game.setRound(game.getRound() + 1);
                    // refresh window
                    dBoard.repaint();
                    roundLabel.setText("Round: " + String.valueOf(game.getRound()));
                    playerAScore.setText("Score: " + String.valueOf(players.get(0).getScore()));
                    playerBScore.setText("Score: " + String.valueOf(players.get(1).getScore()));
                    window.repaint();
                } else {
                    newGame();
                }
            }
        });

        JButton generateBoardBtn = new JButton("Generate Board");
        generateBoardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        bottomPanel.add(generateBoardBtn);
        bottomPanel.add(playButton);
        return bottomPanel;

    }

    private static JPanel buildPlayerPanel(DrawingBoard dBoard, Player player, JLabel playerName,
            JLabel playerScorJLabel, JComboBox<String> playerType) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerScorJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerType.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerName.setAlignmentY(Component.CENTER_ALIGNMENT);
        playerScorJLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        playerType.setAlignmentY(Component.CENTER_ALIGNMENT);

        playerName.setText("Player Name: " + player.getName());
        playerScorJLabel.setText(String.valueOf("Score: " + String.valueOf(player.getScore())));
        playerPanel.add(playerName);
        playerPanel.add(playerScorJLabel);

        playerType.removeAllItems();
        playerType.addItem("Player");
        playerType.addItem("HeuristicPlayer");
        playerType.addItem("MinMaxPlayer");

        playerPanel.add(playerType);

        return playerPanel;
    }

    static void newGame() {
        // new board
        game = new Game();
        aBoard.createBoard();
        aBoard.createElementBoard();
        roundLabel.setText("Round: " + String.valueOf(game.getRound()));

        // new players
        playerA = new Player(0, "Alexa", 0, aBoard);
        playerB = new Player(1, "Cortana", 0, aBoard);
        playerAScore.setText("Score: " + playerA.getScore());
        playerBScore.setText("Score: " + playerB.getScore());

        players.clear();

        switch (playerAType.getSelectedIndex()) {
        case 0:
            players.add(playerA);
            break;
        case 1:
            players.add(new HeuristicPlayer(playerA));
            break;
        case 2:
            players.add(new MinMaxPlayer(playerA));
            break;
        default:
            players.add(playerA);
            break;
        }

        switch (playerBType.getSelectedIndex()) {
        case 0:
            players.add(playerB);
            break;
        case 1:
            players.add(new HeuristicPlayer(playerB));
            break;
        case 2:
            players.add(new MinMaxPlayer(playerB));
            break;
        default:
            players.add(playerB);
            break;
        }

        // draw board
        dBoard = new DrawingBoard(aBoard, players);

        dBoard.repaint();
        window.repaint();
    }

    static boolean checkForWinner(Player player) {
        if (player.getTile() >= aBoard.getM() * aBoard.getN()) {
            return true;
        }
        return false;
    }

}
