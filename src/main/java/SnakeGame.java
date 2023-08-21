import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // storing segments of snake body
import java.util.Random; // getting random x,y values on screen
import javax.swing.*;

// inheriting properties from JPanel
public class SnakeGame extends JPanel implements ActionListener, KeyListener {




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
    ArrayList<Tile> snakeBody;

    // Food
    Tile food;
    Random random;

    // game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;



    // creating new constructor
    SnakeGame(int boardWidth, int boardHeight) {
        // this key word is to distinguish between the 2 board height/width 1 is a parameter and 1 is a field/member of snake game class
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true); // snake game is listening


        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

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
//        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // Snake head
        g.setColor(Color.green);
        // draw rectangle - needing to multiply by tileSize to move it 125px to right and 125px down
//        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize );
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
//            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
           g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        // placing it randomly on the board
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24 0-24 random number
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        // collision is true if x1 is on x2, and if y1 is on y2
      return tile1.x == tile2.x && tile1.y == tile2.y;
    }


    public void move() {
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Snake body
        // each tile needs to catch up to the one before it, before the snake head can move
        // iterating backwards
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                // first member of snake body - the one that comes right after the snake head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                // copy x and y position of the body part that is before it
                // move snake body along with its head
                Tile prevSnakePart = snakeBody.get(i -1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // collide with the snake head
            if(collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y > boardHeight) {
            gameOver = true;
        }


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // update x and y position of the snake
        // every 100 milliseconds we are going to call this actionPerformed, which will call repaint - which basically calls draw over and over
        repaint();

        if(gameOver) {
            gameLoop.stop();
        }

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // do not need
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
