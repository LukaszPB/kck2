package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Label menuTitle;
    private Stage stage;

    @FXML
    private void initialize() {
    }

    @FXML
    protected void newGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("choosingGameView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        stage = (Stage) menuTitle.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void quit() throws IOException {
        stage = (Stage) menuTitle.getScene().getWindow();
        stage.close();
    }
}