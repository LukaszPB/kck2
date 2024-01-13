package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import com.example.projekt2.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LogingController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField name;
    @FXML
    private Label message;
    @FXML
    private void initialize() {
        Image backgroundImage = new Image("file:src/main/resources/com/example/projekt2/image/menu3.jpg");
        String backgroundStyle = "-fx-background-image: url('" + backgroundImage.getUrl() + "'); " +
                "-fx-background-size: cover;";

        borderPane.setStyle(backgroundStyle);
    }
    @FXML
    private void back() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void login() throws IOException {
        try {
            Scanner scanner = new Scanner(new File(StageProperties.USERS_FILE_PATH));
            String[] linia;
            message.setTextFill(Color.RED);

            while (scanner.hasNext()) {
                linia = scanner.nextLine().split(" ");
                if(login.getText().equals("") || password.getText().equals("")) {
                    message.setText("login and password cannot be empty");
                }
                if(linia[0].equals(login.getText()) && linia[1].equals(password.getText())) {
                    User.getInstance().setUsername(linia[0]);
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
                    Stage stage = (Stage) borderPane.getScene().getWindow();
                    stage.setScene(scene);
                }
            }
            if(!login.getText().equals("") && !password.getText().equals("")) {
                message.setText("User not found");
            }

            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void registration() throws IOException {
        try {
            Scanner scanner = new Scanner(new File(StageProperties.USERS_FILE_PATH));
            String[] linia;

            while (scanner.hasNext()) {
                linia = scanner.nextLine().split(" ");
                if(linia[0].equals(login.getText())) {
                    message.setText("Username already taken!");
                    message.setTextFill(Color.RED);
                    return;
                }
                if(login.getText().strip().equals("")) {
                    message.setText("Username can't be empty");
                    message.setTextFill(Color.RED);
                    return;
                }
                if(name.getText().strip().equals("")) {
                    message.setText("Name can't be empty");
                    message.setTextFill(Color.RED);
                    return;
                }
                if(password.getText().strip().equals("")) {
                    message.setText("Password can't be empty");
                    message.setTextFill(Color.RED);
                    return;
                }
            }
            scanner.close();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(StageProperties.USERS_FILE_PATH, true))) {
                writer.write(login.getText() + " " + password.getText() + " " + name.getText());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            User.getInstance().setUsername(login.getText());

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
