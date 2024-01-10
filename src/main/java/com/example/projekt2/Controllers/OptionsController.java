package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class OptionsController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button polishButton;
    @FXML
    private Button englishButton;
    @FXML
    private Button easyButton;
    @FXML
    private Button normalButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    private String fleetType;
    private String difficulty;
    private String allowDuplicates;
    @FXML
    private void initialize() {
        Image backgroundImage = new Image("file:src/main/resources/com/example/projekt2/image/menu3.jpg");
        String backgroundStyle = "-fx-background-image: url('" + backgroundImage.getUrl() + "'); " +
                "-fx-background-size: cover;";

        borderPane.setStyle(backgroundStyle);

        try {
            Scanner scanner = new Scanner(new File(StageProperties.SETTINGS_FILE_PATH));

            fleetType = scanner.nextLine();
            difficulty = scanner.nextLine();
            allowDuplicates = scanner.nextLine();
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(fleetType.equals("polish")) {
            polish();
        }
        else {
            english();
        }
        switch (difficulty) {
            case "easy" -> easy();
            case "normal" -> normal();
            case "hard" -> hard();
        }
        if(allowDuplicates.equals("yes")) {
            yes();
        }
        else {
            no();
        }
    }
    @FXML
    private void easy() {
        difficulty = "easy";
        easyButton.getStyleClass().add("selectedButton");
        normalButton.getStyleClass().remove("selectedButton");
        hardButton.getStyleClass().remove("selectedButton");
    }
    @FXML
    private void normal() {
        difficulty = "normal";
        easyButton.getStyleClass().remove("selectedButton");
        normalButton.getStyleClass().add("selectedButton");
        hardButton.getStyleClass().remove("selectedButton");
    }
    @FXML
    private void hard() {
        difficulty = "hard";
        easyButton.getStyleClass().remove("selectedButton");
        normalButton.getStyleClass().remove("selectedButton");
        hardButton.getStyleClass().add("selectedButton");
    }
    @FXML
    private void polish() {
        fleetType="polish";
        polishButton.getStyleClass().add("selectedButton");
        englishButton.getStyleClass().remove("selectedButton");
    }
    @FXML
    private void english() {
        fleetType="english";
        polishButton.getStyleClass().remove("selectedButton");
        englishButton.getStyleClass().add("selectedButton");
    }
    @FXML
    private void yes() {
        allowDuplicates="yes";
        yesButton.getStyleClass().add("selectedButton");
        noButton.getStyleClass().remove("selectedButton");
    }
    @FXML
    private void no() {
        allowDuplicates="no";
        yesButton.getStyleClass().remove("selectedButton");
        noButton.getStyleClass().add("selectedButton");
    }
    @FXML
    private void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void save() {
        try (FileWriter writer = new FileWriter(StageProperties.SETTINGS_FILE_PATH,false)) {
            writer.write(fleetType + "\n" + difficulty + "\n" + allowDuplicates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
