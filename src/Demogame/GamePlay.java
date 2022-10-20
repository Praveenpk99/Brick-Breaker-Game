package Demogame;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 80;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private BrickGenerator bricks;

    public GamePlay() {
        bricks = new BrickGenerator(4,10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }
    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        bricks.draw((Graphics2D) g);
        //Border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3, 560);
        g.fillRect(0,0,692, 3);
        g.fillRect(0, 560, 692, 3);
        g.fillRect(683,0,3, 560);

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD,30));
        g.drawString(""+score, 590, 30);
        //paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);
        //Ball
        g.setColor(Color.green);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(ballposY>570) {
            play = false;
            ballXdir = -1;
            ballYdir = -2;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game Over Score : "+score, 190, 300);
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press Enter to Restart ", 190, 340);
        }
        if(totalBricks==80) {
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Click Left/Right to Start", 190, 300);
        }
        if(totalBricks==0) {
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Congrats! You Completed the Game  ", 120, 300);
            g.drawString("HighScore : "+score, 250, 340);
            g.setColor(Color.blue);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press Enter to Play Again ", 180, 380);
        }
        g.dispose();
    }

    public void actionPerformed(ActionEvent e){
        timer.start();
        if(play) {
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX+50, 550, 50, 8))) {
                if(ballXdir>0) {
                    ballYdir = -ballYdir;
                } else {
                    ballXdir = -ballXdir;
                    ballYdir = -ballYdir;
                }
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 50, 8))) {
                if(ballXdir<0) {
                    ballYdir = -ballYdir;
                } else {
                    ballXdir = -ballXdir;
                    ballYdir = -ballYdir;
                }
            }
            A:
            for(int i=0; i<bricks.bricks.length; i++) {
                for(int j=0; j<bricks.bricks[0].length; j++) {
                    if(bricks.bricks[i][j] > 0) {
                        int brickX = j*bricks.brickWidth+80;
                        int brickY= i*bricks.brickHeight+50;
                        int brickWidth = bricks.brickWidth;
                        int brickHeight = bricks.brickHeight;
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;
                        if(ballrect.intersects(brickrect)) {
                            if(bricks.bricks[i][j]==1) bricks.setValue(0,i,j);
                            else if(bricks.bricks[i][j]==2) bricks.setValue(1,i,j);
                            totalBricks--;
                            score+=5;
                            if(ballposX+19<=brickrect.x || ballposX+1 >= brickrect.x+brickWidth) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX<0) {
                ballXdir = -ballXdir;
            }
            if(ballposY<0) {
                ballYdir = -ballYdir;
            }
            if(ballposX > 663) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 563) {
                playerX = 583;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <= 23) {
                playerX = 3;
            }
            else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalBricks = 80;
                bricks = new BrickGenerator(4,10);
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX+=20;
    }

    public void moveLeft() {
        play = true;
        playerX-=20;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
