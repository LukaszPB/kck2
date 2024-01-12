package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Label menuTitle;
    @FXML
    private BorderPane borderPane;
    private Stage stage;

    @FXML
    private void initialize() {
        Image backgroundImage = new Image("file:src/main/resources/com/example/projekt2/image/menu1.jpg");
        String backgroundStyle = "-fx-background-image: url('" + backgroundImage.getUrl() + "'); " +
                "-fx-background-size: cover;";

        borderPane.setStyle(backgroundStyle);
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
    @FXML
    protected void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        stage = (Stage) menuTitle.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    protected void options() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("OptionsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        stage = (Stage) menuTitle.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    protected void history() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HistoryView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        stage = (Stage) menuTitle.getScene().getWindow();
        stage.setScene(scene);
    }
}