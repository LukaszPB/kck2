package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoosingGameController {
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private BorderPane borderPane;

    @FXML
    private void initialize() {
        Image backgroundImage = new Image("file:src/main/resources/com/example/projekt2/image/menu2.jpg");
        String backgroundStyle = "-fx-background-image: url('" + backgroundImage.getUrl() + "'); " +
                "-fx-background-size: cover;";

        borderPane.setStyle(backgroundStyle);
        imageView1.setOnMousePressed((event)->{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("battleShipView.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) imageView1.getScene().getWindow();
            stage.setScene(scene);
        });
        imageView2.setOnMousePressed((event)->{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mastermindView.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) imageView2.getScene().getWindow();
            stage.setScene(scene);
        });
    }
    @FXML
    private void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
    }
}
