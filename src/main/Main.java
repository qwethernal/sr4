/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Приветствие");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
