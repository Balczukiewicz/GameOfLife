package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Rand {

    public Rand(Canvas canvas, int[][]statesTab, int tmpX, int tmpY, double xSize, double ySize) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(1.0);
        graphicsContext.setFill(Color.BLUE);
        statesTab[tmpY][tmpX] = 1;
        graphicsContext.fillRect(tmpX * xSize, tmpY * ySize, xSize, ySize);
    }
}
