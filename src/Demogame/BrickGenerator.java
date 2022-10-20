package Demogame;

import java.awt.*;
import java.util.Arrays;

public class BrickGenerator {
    public int bricks[][];
    public int brickWidth;
    public int brickHeight;

    public BrickGenerator(int row, int col) {
        bricks = new int[row][col];
        for(int[] brick : bricks) {
            Arrays.fill(brick, 2);
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g) {
        for(int i=0; i<bricks.length; i++) {
            for(int j=0; j<bricks[0].length; j++) {
                if(bricks[i][j]==1) {
                    g.setColor(Color.cyan);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
                else if(bricks[i][j]==2) {
                    g.setColor(Color.blue);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setValue(int value, int row, int col) {
        bricks[row][col] = value;
    }
}
