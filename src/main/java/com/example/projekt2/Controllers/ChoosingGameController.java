package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import com.example.projekt2.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ChoosingGameController {
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label massage;

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

        ArrayList<String[]> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(StageProperties.GAME_REGISTER_FILE_PATH));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(";");
                if(line[0].equals(User.getInstance().getUsername())) {
                    list.add(line);
                }
            }
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int ile = 0,ileB = 0,ileM = 0;
        String[] date1 = new Date().toString().split(" ");

        for(String[] s : list) {
            String[] date2 = s[5].split(" ");
            if(date1[1].equals(date2[1]) && date1[2].equals(date2[2]) && date1[5].equals(date2[5])) {
                ile++;
            }
            if(s[1].equals("Battleship")) {
                ileB++;
            }
            if(s[1].equals("Mastermind")) {
                ileM++;
            }
        }
        if(ile > 10) {
            massage.setText("you've already played 10 games, that's enough for today");
        }
        else if(ileB == 0 && ileM > 5) {
            massage.setText("you haven't tried battleships yet, maybe it's worth it");
        }
        else if(ileM == 0 && ileB > 5) {
            massage.setText("you haven't tried mastermind yet, maybe it's worth it");
        }
    }
    @FXML
    private void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
    }
}
