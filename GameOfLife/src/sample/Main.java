package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Game of Life");
        primaryStage.setResizable(false);
        showWindow();
    }

    public void showWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
