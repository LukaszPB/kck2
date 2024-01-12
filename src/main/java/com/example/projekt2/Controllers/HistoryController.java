package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import com.example.projekt2.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HistoryController {
    @FXML
    private VBox vBox;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button right;
    @FXML
    private Button left;
    private Stage stage;
    private int idx = 0;
    private Label[] labels = new Label[5];
    private ArrayList<String> list = new ArrayList<>();

    @FXML
    private void initialize() {
        Image backgroundImage = new Image("file:src/main/resources/com/example/projekt2/image/menu1.jpg");
        String backgroundStyle = "-fx-background-image: url('" + backgroundImage.getUrl() + "'); " +
                "-fx-background-size: cover;";

        borderPane.setStyle(backgroundStyle);

        Image rightArrow = new Image("file:src/main/resources/com/example/projekt2/image/right_arrow.jpg");
        String rightArrowStyle = "-fx-background-image: url('" + rightArrow.getUrl() + "'); " +
                "-fx-background-size: cover;";

        right.setStyle(rightArrowStyle);

        Image leftArrow = new Image("file:src/main/resources/com/example/projekt2/image/left_arrow.jpg");
        String leftArrowStyle = "-fx-background-image: url('" + leftArrow.getUrl() + "'); " +
                "-fx-background-size: cover;";

        left.setStyle(leftArrowStyle);

        try {
            Scanner scanner = new Scanner(new File(StageProperties.GAME_REGISTER_FILE_PATH));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(";");
                String[] date = line[5].split(" ");
                if(line[0].equals(User.getInstance().getUsername())) {
                    list.add(line[1] + " " + line[3] + " " +
                            date[2] + " " + date[1] + " " + date[3] + " " + date[5]);
                }
            }
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(list);
        for(int i=0; i<5;i++) {
            Label label = new Label();
            labels[i] = label;
            label.getStyleClass().add("record");
            vBox.getChildren().add(label);
        }
        print();
    }
    private void print() {
        int i=0;
        for(;i<5 && i+idx<list.size();i++) {
            labels[i].setText(list.get(i+idx));
        }
        for(;i<5;i++) {
            labels[i].setText("");
        }
    }
    @FXML
    protected void left() {
        if(idx>=5) {
            idx-=5;
            print();
        }
    }
    @FXML
    protected void right() {
        if(idx+5<=list.size()) {
            idx+=5;
            print();
        }
    }
    @FXML
    protected void highScore() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HighScoreView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    protected void back() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
    }
}
