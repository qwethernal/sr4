package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import menu.MenuScreenController;

public class MainScreenController implements Initializable {
    @FXML
    private Label example;
    
    @FXML
    private TextField fieldDate;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate currentDate = LocalDate.now();
        example.setText("Пример даты: " + currentDate);
    }

    @FXML
    void loginClicked(ActionEvent event) {
        String dateText = fieldDate.getText();
        if (isValidDate(dateText)) {
            LocalDate inputDate = LocalDate.parse(dateText);
            LocalDate currentDate = LocalDate.now();
            if (!inputDate.isBefore(currentDate)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/MenuScreen.fxml"));
                    Parent root = loader.load();
                    MenuScreenController controller = loader.getController();
                    controller.setDiscountStartDate(inputDate);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Введенная дата должна быть не ранее текущей даты.");
            }
        } else {
            showAlert("Некорректный формат даты. Пожалуйста, введите дату в формате YYYY-MM-DD.");
        }
    }
    private boolean isValidDate(String dateText) {
        try {
            LocalDate.parse(dateText, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
