import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // storing segments of snake body
import java.util.Random; // getting random x,y values on screen
import javax.swing.*;

// inheriting properties from JPanel
public class SnakeGame extends JPanel implements ActionListener {


    // private so that only SnakeGame class has access to it
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // accessing the class named Tile and creating a new instance called snakeHead
    // Snake
    Tile snakeHead;

    // Food
    Tile food;

    // game logic
    Timer gameLoop;

    Random random;

    // creating new constructor
    SnakeGame(int boardWidth, int boardHeight) {
        // this key word is to distinguish between the 2 board height/width 1 is a parameter and 1 is a field/member of snake game class
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        snakeHead = new Tile(5, 5);
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        // super keyword - refers to superclass (parent) objects, super is inheriting
        // used to call superclass methods, and to access the superclass constructor
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // grid
        // boardWidth/tileSize = 24
//        for (int i = 0; i < boardWidth/tileSize; i++) {
//            // (x1, y1, x2, y2) - starting point and ending point
//            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight); // vertical lines
//            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); // horizontal lines
//        }

        // Food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        // snake
        g.setColor(Color.green);
        // draw rectangle - needing to multiply by tileSize to move it 125px to right and 125px down
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize );
    }

    public void placeFood() {
        // placing it randomly on the board
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24 0-24 random number
        food.y = random.nextInt(boardHeight/tileSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // every 100 miliseconds we are going to call this actionPerformed, which will call repaint - which basically calls draw over and over
        repaint();
    }
}
