package tetris;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Tetris extends JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/scores";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "gabi";
    private final Piece[] Tetraminos = {
            new Piece(0), new Piece(1), new Piece(2), new Piece(3),new Piece(4), new Piece(5),
            new Piece(6), new Piece(7), new Piece(8)
    };

    private final Color[] tetraminoColors = {
            Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.white, Color.red, Color.magenta, Color.pink
    };
    private Point pieceOrigin;
    private int currentPiece;
    private int rotation;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();
    private boolean isGameOver;
    private String playerName;
    private long score;
    private Color[][] well;
    // Creates a border around the well and initializes the dropping piece
    private void init() {
        isGameOver = false;
        well = new Color[20][30];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 29; j++) {
                if (i == 0 || i == 19 || j == 28) {
                    well[i][j] = Color.GRAY;
                } else {
                    well[i][j] = Color.BLACK;
                }
            }
        }
        newPiece();
        getPlayerName();
    }
    private void getPlayerName() {
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", null, JOptionPane.PLAIN_MESSAGE);
    }

    // Put a new, random piece into the dropping position
    public void newPiece() {
        pieceOrigin = new Point(9, 2);
        rotation = 0;
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2 , 3, 4, 5, 6, 7, 8);
            Collections.shuffle(nextPieces);
        }
        currentPiece = nextPieces.get(0);
        nextPieces.remove(0);
    }
    // Collision test for the dropping piece
    private boolean collidesAt(int x, int y, int rotation) {
        for (Point p : Tetraminos[currentPiece].getPiece()[rotation]) {
            if (well[p.x + x][p.y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }
    // Rotate the piece clockwise or counter-clockwise
    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
        repaint();
    }
    // Move the piece left or right
    public void move(int i) {
        if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
            pieceOrigin.x += i;
        }
        repaint();
    }
    // Drops the piece one line or fixes it to the well if it can't drop
    public void dropDown() {
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            pieceOrigin.y += 1;
        } else {
            fixToWell();
            if (isGameOver) {
                gameOver();
                return;
            }
        }
        repaint();
    }
    // Method to handle the "Game Over" state
    public void gameOver() {
        Object[] options = {"Quit", "Play Again"};
        int choice = JOptionPane.showOptionDialog(this, "Game Over!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        exportScoresToDatabase(playerName, score);
        if (choice == 0) {
            System.exit(0); // Quit the game
        } else {
            // Reset the game
            score = 0;
            init();
        }
        init();
    }
    // Make the dropping piece part of the well, so it is available for
    // collision detection.
    public void fixToWell() {
        for (Point p : Tetraminos[currentPiece].getPiece()[rotation]) {
            well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetraminoColors[currentPiece];
        }
        clearRows();
        newPiece();

        // Check for Game Over
        if (collidesAt(pieceOrigin.x, pieceOrigin.y, rotation)) {
            isGameOver = true;
        }
    }
    public void deleteRow(int row) {
        for (int j = row-1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j+1] = well[i][j];
            }
        }
    }
    // Clear completed rows from the field and award score according to
    // the number of simultaneously cleared rows.
    public void clearRows() {
        boolean gap;
        int numClears = 0;

        for (int j = 27; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 19; i++) {
                if (well[i][j] == Color.BLACK) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(j);
                j += 1;
                numClears += 1;
            }
        }

        switch (numClears) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }
    // Draw the falling piece
    private void drawPiece(Graphics g) {
        g.setColor(tetraminoColors[currentPiece]);
        for (Point p : Tetraminos[currentPiece].getPiece()[rotation]) {
            g.fillRect((p.x + pieceOrigin.x) * 26,
                    (p.y + pieceOrigin.y) * 26,
                    25, 25);
        }
    }
    @Override
    public void paintComponent(Graphics g)
    {

        g.fillRect(0, 0, 26*20, 26*29);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 29; j++) {
                g.setColor(well[i][j]);
                g.fillRect(26*i, 26*j, 25, 25);
            }
        }
        // Display the score
        g.setFont(new Font("Arial",Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 16*12, 27);

        // Draw the currently falling piece
        drawPiece(g);
    }
    public static void main(String[] args) {
        JFrame f = new JFrame("Tetris");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(20*26+10, 26*29+25);
        f.setIconImage(new ImageIcon(new byte[0]).getImage());
        f.setVisible(true);

        final Tetris game = new Tetris();
        game.init();
        f.add(game);
        // Keyboard controls
        f.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        game.rotate(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.rotate(+1);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.move(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.move(+1);
                        break;
                    case KeyEvent.VK_SPACE:
                        game.dropDown();
                        game.score += 1;
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        });
        // Make the falling piece drop every second
        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1500);
                        game.dropDown();
                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();
    }
    public static void exportScoresToDatabase(String playerName, long userScore) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Insert the player name and score into the database
            String insertQuery = "INSERT INTO score (player_name, player_score) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, playerName);
                statement.setLong(2, userScore);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}