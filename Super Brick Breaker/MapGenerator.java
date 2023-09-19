import java.awt.*;

class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    // create a 3 row by 7 column (3x7) brick wall
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void drawBrick(Graphics2D brick) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
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
        map[row][col] = value; // brick value becomes 0 when ball hits brick
    }
}