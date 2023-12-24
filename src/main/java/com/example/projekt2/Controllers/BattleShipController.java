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
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private boolean newGame;
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
        gameEnded = true;
        newGame = true;

        Image backgroundImage = new Image("file:src/main/resources/com/example/projekt2/image/water.jpg");
        String backgroundStyle = "-fx-background-image: url('" + backgroundImage.getUrl() + "'); " +
                "-fx-background-size: cover;";

        borderPane.setStyle(backgroundStyle);
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

            rectangle.setOnScroll((event)-> {

                switch (s.getDirection()) {
                    case 'r' -> s.setDirection('d');
                    case 'l' -> s.setDirection('u');
                    case 'u' -> s.setDirection('r');
                    case 'd' -> s.setDirection('l');
                }

                rectangle.setHeight(s.getWidth());
                rectangle.setWidth(s.getHeight());

                s.setLayoutX(event.getSceneX());
                s.setLayoutY(event.getSceneY());
                rectangle.fireEvent(new MouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0, 0,
                        0, MouseButton.PRIMARY, 1, true, true, true, true,
                        true, true, true, true, true, true, null));
            });
            rectangle.setOnMousePressed((event)-> {
                s.setLayoutX(event.getSceneX());
                s.setLayoutY(event.getSceneY());
            });
            rectangle.setOnMouseDragged((event) -> {
                if(event.getSceneX()-s.getLayoutX() >= 30 && rectangle.getLayoutX() <= 270 -
                        (s.getDirection() == 'u' || s.getDirection() == 'd' ? 30 * (s.getSize()-1) : 0)) {
                    rectangle.setLayoutX(rectangle.getLayoutX()+30);
                    s.setLayoutX(event.getSceneX());
                }
                if(event.getSceneX()-s.getLayoutX() <= -30 && rectangle.getLayoutX() >= 60) {
                    rectangle.setLayoutX(rectangle.getLayoutX()-30);
                    s.setLayoutX(event.getSceneX());
                }
                if(event.getSceneY()-s.getLayoutY() >= 30 && rectangle.getLayoutY() <= 270 -
                        (s.getDirection() == 'l' || s.getDirection() == 'r' ? 30 * (s.getSize()-1) : 0)) {
                    rectangle.setLayoutY(rectangle.getLayoutY()+30);
                    s.setLayoutY(event.getSceneY());
                }
                if(event.getSceneY()-s.getLayoutY() <= -30 && rectangle.getLayoutY() >= 60) {
                    rectangle.setLayoutY(rectangle.getLayoutY()-30);
                    s.setLayoutY(event.getSceneY());
                }
            });
            rectangle.setOnMouseReleased((event) -> {
                int x,y;
                switch (s.getDirection()) {
                    case 'l' -> {
                        x = (int) (rectangle.getLayoutY()/30-1+s.getSize());
                        y = (int) (rectangle.getLayoutX()/30-1);
                    }
                    case 'u' -> {
                        x = (int) (rectangle.getLayoutY()/30-1);
                        y = (int) (rectangle.getLayoutX()/30-2+s.getSize());
                    }
                    default -> {
                        x = (int) (rectangle.getLayoutY()/30-1);
                        y = (int) (rectangle.getLayoutX()/30-1);
                    }
                }
                Ship ship = s.copy(x,y,s.getDirection());

                human.removeShip(s);
                if(human.validateShip(ship)) {
                    s.setCords(ship.getCords());
                    human.addShip(s);
                    s.setWidth(rectangle.getWidth());
                    s.setHeight(rectangle.getHeight());
                }
                else {
                    human.addShip(s);

                    if(rectangle.getHeight() != s.getHeight()) {
                        switch (s.getDirection()) {
                            case 'r' -> s.setDirection('u');
                            case 'l' -> s.setDirection('d');
                            case 'u' -> s.setDirection('l');
                            case 'd' -> s.setDirection('r');
                        }
                    }

                    s.generateRectangle();
                }
            });
        }
    }
    protected void playerMove(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton && !gameEnded &&
                Objects.equals(clickedButton.getText(),"")) {

            Point p = new Point((int) clickedButton.getLayoutY()/30-1,(int) clickedButton.getLayoutX()/30-1);
            if(computer.getHit(p)) {
                clickedButton.setText(" ");
                clickedButton.getStyleClass().add("less-red-square");
                if(computer.getShip(p).getDestroyed()) {
                    destroyedShip(computer.getShip(p),anchorPaneLeft);
                }
            }
            else {
                clickedButton.setText(" ");
                clickedButton.getStyleClass().add("green-square");
                computerMove(2);
            }
            if(computer.wasDefeated()) {
                System.out.println("Wygrałeś");
                title.getStyleClass().add("green");
                borderPane.getStyleClass().add("green_frame");
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

                button.getStyleClass().remove("blue-square");
                button.getStyleClass().add("red-square");

                if(human.wasDefeated()) {
                    System.out.println("Przegrałeś");
                    borderPane.getStyleClass().add("red_frame");
                    title.getStyleClass().add("red");
                    gameEnded = true;
                    break;
                }
            }
            else {
                button.getStyleClass().add("green-square");
                //button.getStyleClass().add("green");
                break;
            }
        }
    }
    private void destroyedShip(Ship ship, AnchorPane anchorPane) {
        for(Point p : ship.getCords()) {
            Button button = getButton(anchorPane,p.getX()+1, p.getY()+1);
            if(button != null) {
                button.getStyleClass().remove("less-red-square");
                button.getStyleClass().add("red-square");
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
    @FXML
    private void startGame() {
        if(newGame) {
            for(Node node : anchorPaneRight.getChildren()) {
                if(node instanceof Button && human.hasShipOnPoint(
                        new Point((int) node.getLayoutY()/30-1,(int) node.getLayoutX()/30-1)) ) {
                    node.getStyleClass().add("blue-square");
                }
                if(node instanceof Rectangle) {
                    node.getStyleClass().add("transparent");
                }
            }
            gameEnded=false;
            newGame=false;
        }
    }
}
