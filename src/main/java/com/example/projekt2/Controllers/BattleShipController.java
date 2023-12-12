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
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BattleShipController{
    @FXML
    private GridPane rightGrid;
    @FXML
    private GridPane leftGrid;
    @FXML
    private AnchorPane anchorPane;
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
            Text text1 = new Text(i + " ");
            Text text2 = new Text(i + " ");

            text1.setFont(StageProperties.BOLD_FONT);
            text2.setFont(StageProperties.BOLD_FONT);

            rightGrid.add(text1,0,i);
            leftGrid.add(text2,0,i);

            Text text3 = new Text("   " + (char) (i+96));
            Text text4 = new Text("   " + (char) (i+96));

            text3.setFont(StageProperties.BOLD_FONT);
            text4.setFont(StageProperties.BOLD_FONT);

            rightGrid.add(text3,i,0);
            leftGrid.add(text4,i,0);
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

                rightGrid.add(button1,i,j);
                leftGrid.add(button2,i,j);
           }
        }
    }
    private void setShips() {
        Rectangle rectangle = new Rectangle(30,30);
        rectangle.getStyleClass().add("red-square");
        rectangle.setOnMouseDragged((event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            rectangle.setLayoutX(x);
            rectangle.setLayoutY(y);
        });
        anchorPane.getChildren().add(rectangle);
    }

    protected void playerMove(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton && !gameEnded &&
                Objects.equals(clickedButton.getText(),"")) {

            Integer rowIndex = GridPane.getRowIndex(clickedButton);
            Integer colIndex = GridPane.getColumnIndex(clickedButton);

            Point p = new Point(rowIndex-1,colIndex-1);
            if(computer.getHit(p)) {
                clickedButton.setText("X");
                if(computer.getShip(p).getDestroyed()) {
                    destroyedShip(computer.getShip(p),leftGrid);
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
    private Button getButton(GridPane grid, int x, int y) {
        for(Node node : grid.getChildren()) {
            if(GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                return node instanceof Button button ? button : null;
            }
        }
        return null;
    }
    private void computerMove(int difficulty) {
        while(true) {
            Point p = computer.shoot(difficulty);
            Button button = getButton(rightGrid,p.getX()+1,p.getY()+1);
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
    private void destroyedShip(Ship ship, GridPane grid) {
        for(Point p : ship.getCords()) {
            Button button = getButton(grid,p.getX()+1, p.getY()+1);
            if(button != null) {
                button.getStyleClass().add("red");
            }
        }
    }
    @FXML
    private void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) rightGrid.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void reset() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("battleShipView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) rightGrid.getScene().getWindow();
        stage.setScene(scene);
    }
}
