package com.example.projekt2.Controllers;

import com.example.projekt2.BattleShip.GameLogic.Computer;
import com.example.projekt2.BattleShip.GameLogic.Human;
import com.example.projekt2.BattleShip.Point;
import com.example.projekt2.BattleShip.Ships.*;
import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BattleShipController{
    @FXML
    private AnchorPane anchorPaneRight;
    @FXML
    private AnchorPane anchorPaneLeft;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label title;
    private Computer computer;
    private Human human;
    private boolean gameEnded;
    @FXML
    private void initialize() {
        ArrayList<Ship> prototype = new ArrayList<Ship>(Arrays.asList(new FourPointShip(),
                new ThreePointShip(), new ThreePointShip(), new TwoPointShip(), new TwoPointShip(),
                new TwoPointShip(), new OnePointShip(), new OnePointShip(), new OnePointShip(),
                new OnePointShip()));

        computer = new Computer(prototype);
        human = new Human(prototype);

        for(Ship ship : new Computer(prototype).getShips()) {
            human.addShip(ship);
        }

        setBoard();
        setShips();
        gameEnded = false;
    }
    private void setBoard() {
        for(int i=1;i<=10;i++) {
            Button text1 = new Button(i + "");
            Button text2 = new Button(i + "");

            text1.getStyleClass().add("label_button");
            text2.getStyleClass().add("label_button");

            text1.setLayoutY(i*StageProperties.FIELD_SIZE);
            anchorPaneRight.getChildren().add(text1);

            text2.setLayoutY(i*StageProperties.FIELD_SIZE);
            anchorPaneLeft.getChildren().add(text2);

            Button text4 = new Button("" + (char) (i+96));
            Button text3 = new Button("" + (char) (i+96));

            text3.getStyleClass().add("label_button");
            text4.getStyleClass().add("label_button");

            text3.setLayoutX(i*StageProperties.FIELD_SIZE);
            anchorPaneRight.getChildren().add(text3);
            text4.setLayoutX(i*StageProperties.FIELD_SIZE);
            anchorPaneLeft.getChildren().add(text4);
        }


        for(int i=1;i<=10;i++) {
            for(int j=1;j<=10;j++) {
                Button button1 = new Button();
                Button button2 = new Button();

                button1.getStyleClass().add("custom_button");
                button2.getStyleClass().add("custom_button");

                button2.setOnAction(this::playerMove);

                if(human.hasShipOnPoint(new Point(j-1,i-1))) {
                    button1.setText("X");
               }

                button1.setLayoutX(i*StageProperties.FIELD_SIZE);
                button1.setLayoutY(j*StageProperties.FIELD_SIZE);
                anchorPaneRight.getChildren().add(button1);

                button2.setLayoutX(i*StageProperties.FIELD_SIZE);
                button2.setLayoutY(j*StageProperties.FIELD_SIZE);
                anchorPaneLeft.getChildren().add(button2);
           }
        }
    }
    private void setShips() {
        for(Ship s : human.getShips()) {
            Rectangle rectangle = s.getRectangle();
            anchorPaneRight.getChildren().add(s.getRectangle());

            rectangle.setOnMouseClicked((event)-> {
                s.setLayoutX(event.getSceneX());
                s.setLayoutY(event.getSceneY());
            });
            rectangle.setOnMouseDragged((event) -> {
                if(event.getSceneX()-s.getLayoutX() >= 30 && rectangle.getLayoutX() <= 270) {
                    rectangle.setLayoutX(rectangle.getLayoutX()+30);
                    s.setLayoutX(event.getSceneX());
                    for(Point p : s.getCords()) {
                        p.setX(p.getX()+1);
                    }
                }
                if(event.getSceneX()-s.getLayoutX() <= -30 && rectangle.getLayoutX() >= 60) {
                    rectangle.setLayoutX(rectangle.getLayoutX()-30);
                    s.setLayoutX(event.getSceneX());
                    for(Point p : s.getCords()) {
                        p.setX(p.getX()-1);
                    }
                }
                if(event.getSceneY()-s.getLayoutY() >= 30 && rectangle.getLayoutY() <= 270) {
                    rectangle.setLayoutY(rectangle.getLayoutY()+30);
                    s.setLayoutY(event.getSceneY());
                    for(Point p : s.getCords()) {
                        p.setY(p.getY()+1);
                    }
                }
                if(event.getSceneY()-s.getLayoutY() <= -30 && rectangle.getLayoutY() >= 60) {
                    rectangle.setLayoutY(rectangle.getLayoutY()-30);
                    s.setLayoutY(event.getSceneY());
                    for(Point p : s.getCords()) {
                        p.setY(p.getY()-1);
                    }
                }
            });
        }
    }
    protected void playerMove(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton && !gameEnded &&
                Objects.equals(clickedButton.getText(),"")) {

            Point p = new Point((int) clickedButton.getLayoutY()/30-1,(int) clickedButton.getLayoutX()/30-1);
            if(computer.getHit(p)) {
                clickedButton.setText("X");
                if(computer.getShip(p).getDestroyed()) {
                    destroyedShip(computer.getShip(p),anchorPaneLeft);
                }
            }
            else {
                clickedButton.setText("O");
                computerMove(2);
            }
            if(computer.wasDefeated()) {
                System.out.println("Wygrałeś");
                borderPane.getStyleClass().add("green_frame");
                title.getStyleClass().add("green");
                gameEnded = true;
            }
        }
    }
    private Button getButton(AnchorPane anchorPane, int x, int y) {
        for(Node node : anchorPane.getChildren()) {
            if((int) node.getLayoutY()/30 == x && (int) node.getLayoutX()/30 == y) {
                return node instanceof Button button ? button : null;
            }
        }
        return null;
    }
    private void computerMove(int difficulty) {
        while(true) {
            Point p = computer.shoot(difficulty);
            Button button = getButton(anchorPaneRight,p.getX()+1,p.getY()+1);
            if(button == null) {
            }
            else if(human.getHit(p)) {
                if(human.getShip(p).getDestroyed()) {
                    computer.acknowledgeShipDestruction(human.getShip(p));
                    computer.setTarget(null);
                }
                else {
                    computer.setTarget(human.getShip(p));
                }
                button.setText("X");
                button.getStyleClass().add("red");

                if(human.wasDefeated()) {
                    System.out.println("Przegrałeś");
                    borderPane.getStyleClass().add("red_frame");
                    title.getStyleClass().add("red");
                    gameEnded = true;
                    break;
                }
            }
            else {
                button.setText("O");
                button.getStyleClass().add("green");
                break;
            }
        }
    }
    private void destroyedShip(Ship ship, AnchorPane anchorPane) {
        for(Point p : ship.getCords()) {
            Button button = getButton(anchorPane,p.getX()+1, p.getY()+1);
            if(button != null) {
                button.getStyleClass().add("red");
            }
        }
    }
    @FXML
    private void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) anchorPaneRight.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void reset() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("battleShipView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) anchorPaneRight.getScene().getWindow();
        stage.setScene(scene);
    }
}
