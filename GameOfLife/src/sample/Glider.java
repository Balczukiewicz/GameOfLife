package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
// klasa stanu Glider

public class Glider {


    public Glider(Canvas canvas, int[][]statesTab, int tmpX, int tmpY, double xSize, double ySize) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(1.0);
        graphicsContext.setFill(Color.BLUE);

        statesTab[tmpY][tmpX] = 1;
        statesTab[tmpY][tmpX + 1] = 1;
        statesTab[tmpY + 1][tmpX - 1] = 1;
        statesTab[tmpY + 1][tmpX] = 1;
        statesTab[tmpY + 2][tmpX + 1] = 1;

        graphicsContext.fillRect(tmpX * xSize, tmpY * ySize, xSize, ySize);
        graphicsContext.fillRect((tmpX + 1) * xSize, tmpY * ySize, xSize, ySize);
        graphicsContext.fillRect((tmpX - 1) * xSize, (tmpY + 1) * ySize, xSize, ySize);
        graphicsContext.fillRect((tmpX) * xSize, (tmpY + 1) * ySize, xSize, ySize);
        graphicsContext.fillRect((tmpX + 1) * xSize, (tmpY + 2) * ySize, xSize, ySize);
    }
}
