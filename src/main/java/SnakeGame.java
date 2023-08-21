import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // storing segments of snake body
import java.util.Random; // getting random x,y values on screen
import javax.swing.*;

// inheriting properites from JPanel
public class SnakeGame extends JPanel {
    // private so that only SnakeGame class has access to it
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this. x = x;
            this.y = x;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // accessing the class named Tile and creating a new instance called snakeHead
    Tile snakeHead;

    // creating new constructor
    SnakeGame(int boardWidth, int boardHeight) {
        // this key word is to distinguish between the 2 board height/width 1 is a parameter and 1 is a field/member of snake game class
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        snakeHead = new Tile(5, 5);
    }

    public void paintComponent(Graphics g) {
        // super keyword - refers to superclass (parent) objects, super is inheriting
        // used to call superclass methods, and to access the superclass constructor
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // snake
        g.setColor(Color.green);
        // draw rectangle
        g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize );
    }
}
