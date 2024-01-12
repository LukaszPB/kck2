package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
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
import java.util.Comparator;
import java.util.Scanner;

public class HighScoreController {
    @FXML
    private VBox vBox;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button right;
    @FXML
    private Button left;
    @FXML
    private Label label;
    private Stage stage;
    private int idx = 0;
    private Label[] labels = new Label[5];
    private ArrayList<String> list = new ArrayList<>();
    private String[] titles = new String[] {
            "Battleship easy",
            "Battleship normal",
            "Battleship hard",
            "Mastermind without duplicates",
            "Mastermind with duplicates"
    };
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
                if(line[3].equals("win")) {
                    list.add(line[1] + " " + line[2] + " " + line[0] + " " + line[4]);
                }

            }
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<5;i++) {
            Label label = new Label();
            labels[i] = label;
            label.getStyleClass().add("record");
            vBox.getChildren().add(label);
        }
        labels[0].getStyleClass().add("gold");
        labels[1].getStyleClass().add("silver");
        labels[2].getStyleClass().add("bronze");
        print();
    }
    private void print() {
        label.setText(titles[idx]);
        ArrayList<String> list_tmp = new ArrayList<>();
        String[] name = titles[idx].split(" ");
        switch (name[1]) {
            case "easy", "without" -> name[1] = "0";
            case "normal", "with" -> name[1] = "1";
            case "hard" -> name[1] = "2";
        }

        for(String s : list) {
            String[] tab = s.split(" ");
            if(name[0].equals(tab[0]) && name[1].equals(tab[1])) {
                list_tmp.add(tab[2] + " " + tab[3]);
            }
        }

        list_tmp.sort(Comparator.comparingInt(x -> Integer.parseInt(x.split(" ")[1])));

        int i=0;
        for(;i<5 && i<list_tmp.size();i++) {
            labels[i].setText(list_tmp.get(i));
        }
        for(;i<5;i++) {
            labels[i].setText("");
        }
    }
    @FXML
    protected void left() {
        if(idx>0) {
            idx--;
            print();
        }
    }
    @FXML
    protected void right() {
        if(idx<4) {
            idx++;
            print();
        }
    }
    @FXML
    protected void history() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HistoryView.fxml"));
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
