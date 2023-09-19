import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = true;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK); // Background
        g.fillRect(1, 1, 692, 592);

        map.drawBrick((Graphics2D)g);

        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.WHITE); // Player
        g.fillRect(playerX, 550, 100, 12);

        g.setColor(Color.CYAN); // Ball
        g.fillOval(ballPosX, ballPosY, 20, 20);

        g.setColor(Color.GREEN); // Score
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString("Score: " + score, 520, 30);

        if (totalBricks <= 0) { // if all bricks are destroyed then you win
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(new Color(0xFF1B1B));
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            g.drawString("You Won! Score: " + score, 190, 300);

            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Enter Key to Play Again.", 230, 350);
        }

        if (ballPosY > 570) { // if ball goes below the paddle then you lose
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(new Color(0xFF1B1B));
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            g.drawString("Game Over. Score: " + score, 190, 300);

            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Enter Key to Play Again.", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        timer.start();

        if (play) {
            // If Ball and Player Interact
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = - ballDirY;
            }

            for(int i = 0; i < map.map.length; i++) { // If Ball and Brick Interact
                for(int j = 0; j < map.map[0].length; j++) {  // Test if less than # of columns
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect) ) {
                            map.setBrickVal(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width)
                                ballDirX = -ballDirX;
                            else {
                                ballDirY = -ballDirY;
                            }
                        }
                    }
                }
            }

            ballPosX += ballDirX;
            ballPosY += ballDirY;

            if (ballPosX < 0) { // if ball hits left wall, needs to bounce back
                ballDirX = -ballDirX;
            }

            if (ballPosY < 0) {  // if ball hits top wall, needs to bounce back
                ballDirY = -ballDirY;
            }

            if (ballPosX > 670) { // if ball hits right wall, needs to bounce back
                ballDirX = -ballDirX;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent arg0) { }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) { // right arrow key press = player moves to the right
            if (playerX >= 600) {
                playerX = 600;
            } else {
                playerMoveRight();
            }
        }

        if (arg0.getKeyCode() == KeyEvent.VK_LEFT) { // left arrow key press = player moves to the left
            if (playerX < 10) {
                playerX = 10;
            } else {
                playerMoveLeft();
            }
        }

        // restart game if player hits enter after game is over
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void playerMoveRight() {
        play = true;
        playerX += 50; // amount in pixels
    }

    public void playerMoveLeft() {
        play = true;
        playerX -= 50;
    }

    @Override
    public void keyReleased(KeyEvent arg0) { }
}