import java.awt.*;

class EnvironmentGenerator {
    public int env[][];
    public int brickWidth;
    public int brickHeight;

    // create a 3 row by 7 column (3x7) brick wall
    public EnvironmentGenerator(int row, int col) {
        env = new int[row][col];
        for (int i = 0; i < env.length; i++) {
            for (int j = 0; j < env[0].length; j++) {
                env[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void drawBrick(Graphics2D brick) {
        for (int i = 0; i < env.length; i++) {
            for (int j = 0; j < env[0].length; j++) {
                if (env[i][j] > 0) {
                    brick.setColor(new Color(0xFF1B1B));
                    brick.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    brick.setStroke(new BasicStroke(4));
                    brick.setColor(Color.BLACK);
                    brick.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickVal(int value, int row, int col) {
        env[row][col] = value; // brick value becomes 0 when ball hits brick
    }
}