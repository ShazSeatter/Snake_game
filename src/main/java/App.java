import javax.swing.*;
public class App {

    public static void main(String[] args) throws Exception
    {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight); // board that is 600 x 600
        frame.setLocationRelativeTo(null); // setting window to open @ centre of screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program to terminate when 'x' button is clicked

        // creating new instance of snakeGame
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack(); // places panel inside frame with full dimensions inside panel
    }
}
