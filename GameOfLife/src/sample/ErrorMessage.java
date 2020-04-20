package sample;

import javafx.scene.control.Alert;
// klasa do wypisywania wiadomosci bledu
public class ErrorMessage {
    static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ErrorMessage() {
    }
}
