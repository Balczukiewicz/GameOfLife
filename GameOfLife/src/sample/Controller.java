package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Random;

import javafx.util.Duration;

public class Controller {
    public Canvas canvas;
    public TextField width;
    public TextField length;

    public Timeline timeline;
    public ErrorMessage errorMessage = new ErrorMessage();

    // zmienne odpowiedzialne za wielkosc
    public int wSize, lSize;
    public double sizeX, sizeY;
    // tablica stanu
    public int[][] statesTab;
    public int[][] destTab;
    // generator losowych liczb
    public Random random = new Random();

    // funkcja pobierajaca dane od uzytkownika
    public void loadData() {
        try {
            wSize = Integer.parseInt(width.getText());
            lSize = Integer.parseInt(length.getText());
        } catch (Exception e) {
            errorMessage.showAlert("Values not correct");
        }

        if (wSize < 4 || lSize < 4) {
            errorMessage.showAlert("Length and width must be greater than 4!");
            if (wSize < 4) {
                wSize = 4;
            }
            if (lSize < 4) {
                lSize = 4;
            }
        }
        // dzielimy canvas na odpowiednie wielkosci
        if (wSize < lSize) {
            sizeX = (canvas.getWidth() / lSize);
            sizeY = (canvas.getWidth() / lSize);
        } else if (wSize > lSize) {
            sizeX = (canvas.getHeight() / wSize);
            sizeY = (canvas.getHeight() / wSize);
        } else {
            sizeX = (canvas.getHeight() / wSize);
            sizeY = (canvas.getWidth() / lSize);
        }
    }
    // przycisk show
    public void showBtn() {
        loadData();
        statesTab = new int[lSize][wSize];
        destTab = new int[lSize][wSize];
        drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
    }
    // przycisk beehive
    public void beehiveBtn() {
        try {
            // losujemy "miejsce" w ktorym ma pojawic sie nasz beehive
            int tmpX = random.nextInt(wSize - 3) + 1;
            int tmpY = random.nextInt(lSize - 2);
            // tworzymy beehive i go umieszczamy
            Beehive beehive = new Beehive(canvas, statesTab, tmpX, tmpY, sizeX, sizeY);
            drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
        } catch (Exception e) {
            errorMessage.showAlert("Enter length and width");
        }

    }
    // przycisk glider
    public void gliderBtn() {
        try {
            // losujemy miejsce pojawienia sie glindera
            int tmpX = random.nextInt(wSize - 2) + 1;
            int tmpY = random.nextInt(lSize - 2);
            Glider glider = new Glider(canvas, statesTab, tmpX, tmpY, sizeX, sizeY);
            drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
        } catch (Exception e) {
            errorMessage.showAlert("Enter length and width");
        }

    }
    // przycisk oscilator
    public void oscillatorBtn() {
        try {
            int tmpX = random.nextInt(wSize);
            int tmpY = random.nextInt(lSize - 2);
            Oscillator oscillator = new Oscillator(canvas, statesTab, tmpX, tmpY, sizeX, sizeY);
            drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
        } catch (Exception e) {
            errorMessage.showAlert("Enter length and width");
        }

    }

    public void randomBtn() {
        try {
            for (int i = 0; i < random.nextInt((int) Math.sqrt(lSize * wSize)); i++) {
                int tmpX = random.nextInt(wSize);
                int tmpY = random.nextInt(lSize);
                Rand rand = new Rand(canvas, statesTab, tmpX, tmpY, sizeX, sizeY);
            }
            drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
        } catch (Exception e) {
            errorMessage.showAlert("Enter length and width");
        }

    }

    public void drawGrid(double xDim, double yDim, int xRectCount, int yRectCount) {

        try {
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setLineWidth(1.0);
            graphicsContext.setFill(Color.BLACK);

            if (xRectCount < yRectCount) {
                for (int i = 0; i <= yRectCount; i++) {
                    graphicsContext.strokeLine(0, i * (yDim / yRectCount), xRectCount * (yDim / yRectCount), i * (yDim / yRectCount));
                }

                for (int i = 0; i <= xRectCount; i++) {
                    graphicsContext.strokeLine(i * (yDim / yRectCount), 0, i * (yDim / yRectCount), yDim);
                }
            } else if (xRectCount > yRectCount) {
                for (int i = 0; i <= yRectCount; i++) {
                    graphicsContext.strokeLine(0, i * (yDim / xRectCount), xDim, i * (yDim / xRectCount));
                }

                for (int i = 0; i <= xRectCount; i++) {
                    graphicsContext.strokeLine(i * (yDim / xRectCount), 0, i * (yDim / xRectCount), yRectCount * (yDim / xRectCount));
                }
            } else if (xRectCount == yRectCount) {
                for (int i = 0; i <= yRectCount; i++) {
                    graphicsContext.strokeLine(0, i * (yDim / yRectCount), xDim, i * (yDim / xRectCount));
                }

                for (int i = 0; i <= xRectCount; i++) {
                    graphicsContext.strokeLine(i * (xDim / xRectCount), 0, i * (yDim / xRectCount), yRectCount * (yDim / xRectCount));
                }
            }
        } catch (Exception e) {
            errorMessage.showAlert("Fill X, Y first!!");
        }
    }

    // funkcja ktora pozwala nam recznie definiowac stany

    public void onMouseClickedListener() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event -> {
            double xSizeTmp = 0;
            double ySizeTmp = 0;

            if (wSize > lSize) {
                xSizeTmp = canvas.getHeight() / wSize;
                ySizeTmp = canvas.getHeight() / wSize;
            } else if (wSize < lSize) {
                xSizeTmp = canvas.getWidth() / lSize;
                ySizeTmp = canvas.getWidth() / lSize;
            } else  {
                xSizeTmp = canvas.getHeight() / wSize;
                ySizeTmp = canvas.getWidth() / lSize;
            }

            double finalX = xSizeTmp;
            double finalY = ySizeTmp;

            graphicsContext.setLineWidth(1.0);
            graphicsContext.setFill(Color.BLUE);
            int tmpX = (int) (event.getX() / finalX);
            int tmpY = (int) (event.getY() / finalY);
            try {
                if (statesTab[tmpY][tmpX] == 1) {
                    graphicsContext.setFill(Color.WHITE);
                    statesTab[tmpY][tmpX] = 0;
                } else if (statesTab[tmpY][tmpX] == 0) {
                    graphicsContext.setFill(Color.BLUE);
                    statesTab[tmpY][tmpX] = 1;
                }
                graphicsContext.fillRect(finalX * tmpX, finalY * tmpY, 0.99 * finalX, 0.99 * finalY);
                drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
            } catch (Exception e) {
                errorMessage.showAlert("Something happend");
            }
        });


    }
    // funkcja zasad gry

    public void fillDestTab(int i, int j, int tmp) {
        if (statesTab[i][j] == 0 && tmp == 3) {
            destTab[i][j] = 1;
        } else if (statesTab[i][j] == 1 && (tmp == 2 || tmp == 3)) {
            destTab[i][j] = 1;
        } else if (statesTab[i][j] == 1 && tmp > 3) {
            destTab[i][j] = 0;
        } else if (statesTab[i][j] == 1 && tmp < 2) {
            destTab[i][j] = 0;
        }
    }

    public void createTableStates(int sizeX, int sizeY, int[][] A, int[][] B) {
        for (int[] row : B)
            Arrays.fill(row, 0);

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                /*  in first row ,in last row and int the middle */
                if (i == 0) {
                    if (j == 0) {
                        int tmp = A[sizeY - 1][sizeX - 1] + A[sizeY - 1][j] + A[sizeY - 1][j + 1] +
                                A[i][sizeX - 1] + A[i][j + 1] +
                                A[i + 1][sizeX - 1] + A[i + 1][j] + A[i + 1][j + 1];

                        fillDestTab(i, j, tmp);
                    } else if (j == sizeX - 1) {
                        int tmp = A[sizeY - 1][j - 1] + A[sizeY - 1][j] + A[sizeY - 1][0] +
                                A[i][j - 1] + A[i][0] +
                                A[i + 1][j - 1] + A[i + 1][j] + A[i + 1][0];

                        fillDestTab(i, j, tmp);

                    } else {
                        int tmp = A[sizeY - 1][j - 1] + A[sizeY - 1][j] + A[sizeY - 1][j + 1] +
                                A[i][j - 1] + A[i][j + 1] +
                                A[i + 1][j - 1] + A[i + 1][j] + A[i + 1][j + 1];
                        fillDestTab(i, j, tmp);
                    }
                } else if (i == sizeY - 1) {
                    if (j == 0) {
                        int tmp = A[i - 1][sizeX - 1] + A[i - 1][j] + A[i - 1][j + 1] +
                                A[i][sizeX - 1] + A[i][j + 1] +
                                A[0][sizeX - 1] + A[0][j] + A[0][j + 1];
                        fillDestTab(i, j, tmp);
                    } else if (j == sizeX - 1) {
                        int tmp = A[i - 1][j - 1] + A[i - 1][j] + A[i - 1][0] +
                                A[i][j - 1] + A[i][0] +
                                A[0][j - 1] + A[0][j] + A[0][0];
                        fillDestTab(i, j, tmp);

                    } else {
                        int tmp = A[i - 1][j - 1] + A[i - 1][j] + A[i - 1][j + 1] +
                                A[i][j - 1] + A[i][j + 1] +
                                A[0][j - 1] + A[0][j] + A[0][j + 1];
                        fillDestTab(i, j, tmp);
                    }
                } else {
                    if (j == 0) {
                        int tmp = A[i - 1][sizeX - 1] + A[i - 1][j] + A[i - 1][j + 1] +
                                A[i][sizeX - 1] + A[i][j + 1] +
                                A[i + 1][sizeX - 1] + A[i + 1][j] + A[i + 1][j + 1];
                        fillDestTab(i, j, tmp);
                    } else if (j == sizeX - 1) {
                        int tmp = A[i - 1][j - 1] + A[i - 1][j] + A[i - 1][0] +
                                A[i][j - 1] + A[i][0] +
                                A[i + 1][j - 1] + A[i + 1][j] + A[i + 1][0];
                        fillDestTab(i, j, tmp);

                    } else {
                        int tmp = A[i - 1][j - 1] + A[i - 1][j] + A[i - 1][j + 1] +
                                A[i][j - 1] + A[i][j + 1] +
                                A[i + 1][j - 1] + A[i + 1][j] + A[i + 1][j + 1];
                        fillDestTab(i, j, tmp);
                    }
                }
            }
        }
        for (int[] row : A)
            Arrays.fill(row, 0);

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                A[i][j] = destTab[i][j];
            }
        }
    }

    public void drawNextLifeCycle(int[][] B, int xSize, int ySize) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(1.0);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.setFill(Color.BLUE);
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                if (B[i][j] == 1) {
                    graphicsContext.fillRect(j * sizeX, i * sizeY, sizeX, sizeY);
                }
            }
        }
        drawGrid(canvas.getHeight(), canvas.getWidth(), wSize, lSize);
    }

    public void clearBtn() {
        try {
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setLineWidth(1.0);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (int[] row : statesTab)
                Arrays.fill(row, 0);
        } catch (Exception e) {
            errorMessage.showAlert("Canvas is empty");
        }

    }

    public void setTimeline() {
        EventHandler<ActionEvent> eventHandler = event -> {
            createTableStates(wSize, lSize, statesTab, destTab);
            drawNextLifeCycle(destTab, wSize, lSize);
        };
        KeyFrame keyFrame = new KeyFrame(new Duration(100), eventHandler);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void startBtnAct() {

        try {
            if (wSize < 4 || lSize < 4)
                throw new Exception();
            setTimeline();
            timeline.play();
        } catch (Exception e) {
            errorMessage.showAlert("Enter width and length");
        }
    }

    public void stopBtnAct() {
        timeline.stop();
    }
}

