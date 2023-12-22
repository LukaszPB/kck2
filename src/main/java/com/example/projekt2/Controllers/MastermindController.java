package com.example.projekt2.Controllers;

import com.example.projekt2.Main;
import com.example.projekt2.StageProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MastermindController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label title;
    private boolean gameEnded = false;
    private final Random random = new Random();
    private final Color blankCircleColor = Color.web("#4d2800");
    private final Color[] answer = {
            StageProperties.COLORS[random.nextInt(8)],
            StageProperties.COLORS[random.nextInt(8)],
            StageProperties.COLORS[random.nextInt(8)],
            StageProperties.COLORS[random.nextInt(8)]
    };
    private final BorderStroke redFrame = new BorderStroke(
            Color.web("#FF0000"),
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(10) // Grubość ramki
    );
    private final BorderStroke greenFrame = new BorderStroke(
            Color.web("#39FF14"),
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(10) // Grubość ramki
    );
    private int round;
    @FXML
    private void initialize() {
        setBoard();
    }
    private void setBoard() {
        for(int i=0;i<11;i++) {
            for(int j=0;j<4;j++) {
                Rectangle rectangle = new Rectangle(StageProperties.RECTANGLE_SIZE,StageProperties.RECTANGLE_SIZE);
                Circle circle = new Circle(StageProperties.RECTANGLE_SIZE/3);

                rectangle.setLayoutX(i*StageProperties.RECTANGLE_SIZE
                        +(StageProperties.STAGE_WIDTH-StageProperties.RECTANGLE_SIZE*11-20)/2);
                rectangle.setLayoutY(j*StageProperties.RECTANGLE_SIZE + StageProperties.RECTANGLE_SIZE*2+10);
                rectangle.getStyleClass().add("rectangle");

                circle.setLayoutX(i*StageProperties.RECTANGLE_SIZE+StageProperties.RECTANGLE_SIZE/2
                        +(StageProperties.STAGE_WIDTH-StageProperties.RECTANGLE_SIZE*11-20)/2);
                circle.setLayoutY(j*StageProperties.RECTANGLE_SIZE+StageProperties.RECTANGLE_SIZE*2.5+10);
                circle.getStyleClass().add("circle");

                if(i == 10) {
                    rectangle.getStyleClass().add("rectangle_special");
                    circle.getStyleClass().add("circle_special");
                    rectangle.setLayoutX(rectangle.getLayoutX()+10);
                    circle.setLayoutX(circle.getLayoutX()+10);
                }

                anchorPane.getChildren().addAll(rectangle,circle);
            }
        }
        setHints();
        setColors();
    }
    private void setHints() {
        for(int i=0;i<10;i++) {
            Rectangle rectangle = new Rectangle(StageProperties.RECTANGLE_SIZE,StageProperties.RECTANGLE_SIZE);
            Circle[] circles = {
                    new Circle(StageProperties.RECTANGLE_SIZE/6),
                    new Circle(StageProperties.RECTANGLE_SIZE/6),
                    new Circle(StageProperties.RECTANGLE_SIZE/6),
                    new Circle(StageProperties.RECTANGLE_SIZE/6)};
            for(Circle c : circles) { c.getStyleClass().add("circle_special"); }

            double x = i*StageProperties.RECTANGLE_SIZE
                    +(StageProperties.STAGE_WIDTH-StageProperties.RECTANGLE_SIZE*11-20)/2;
            double y = StageProperties.RECTANGLE_SIZE;

            rectangle.setLayoutX(x);
            rectangle.setLayoutY(y);
            rectangle.getStyleClass().add("rectangle_special");

            x+=StageProperties.RECTANGLE_SIZE/4;
            y+=StageProperties.RECTANGLE_SIZE/4;

            circles[0].setLayoutX(x);
            circles[0].setLayoutY(y);
            circles[1].setLayoutX(x+StageProperties.RECTANGLE_SIZE/2);
            circles[1].setLayoutY(y);
            circles[2].setLayoutX(x);
            circles[2].setLayoutY(y+StageProperties.RECTANGLE_SIZE/2);
            circles[3].setLayoutX(x+StageProperties.RECTANGLE_SIZE/2);
            circles[3].setLayoutY(y+StageProperties.RECTANGLE_SIZE/2);

            anchorPane.getChildren().add(rectangle);
            anchorPane.getChildren().addAll(circles);
        }
    }
    private void setColors() {
        for(int i=0;i<8;i++) {
            Circle circle = new Circle(StageProperties.RECTANGLE_SIZE/3);

            circle.setLayoutX(i*StageProperties.RECTANGLE_SIZE/5*4+StageProperties.RECTANGLE_SIZE/2
                    +(StageProperties.STAGE_WIDTH-StageProperties.RECTANGLE_SIZE*8)/2);
            circle.setLayoutY(StageProperties.RECTANGLE_SIZE*7);

            circle.getStyleClass().add("circle_frame");
            circle.setFill(StageProperties.COLORS[i]);


            circle.setOnMousePressed(event -> {

                Circle c = (Circle) circle.getProperties().get("image");
                if(c != null) {
                    anchorPane.getChildren().remove(c);
                }

                c = new Circle(StageProperties.RECTANGLE_SIZE/3);

                c.setLayoutX(circle.getLayoutX());
                c.setLayoutY(circle.getLayoutY());

                c.getStyleClass().add("circle_frame");
                c.setFill(circle.getFill());

                c.getProperties().put("startX", event.getSceneX() - c.getLayoutX());
                c.getProperties().put("startY", event.getSceneY() - c.getLayoutY());

                anchorPane.getChildren().add(c);

                circle.getProperties().put("image",c);
            });

            circle.setOnMouseDragged(event -> {
                // Oblicz nowe współrzędne kółka na podstawie przesunięcia myszy
                Circle c = (Circle) circle.getProperties().get("image");
                double newX = event.getSceneX() - (double) c.getProperties().get("startX");
                double newY = event.getSceneY() - (double) c.getProperties().get("startY");

                // Ustaw nowe współrzędne kółka
                c.setLayoutX(newX);
                c.setLayoutY(newY);
            });

            circle.setOnMouseReleased(event->{
                Circle c = (Circle) circle.getProperties().get("image");
                Circle circleOnBoard = getCircle(c.getLayoutX(),c.getLayoutY());

                if(circleOnBoard != null && !gameEnded &&
                        (int) (circleOnBoard.getLayoutX()/StageProperties.RECTANGLE_SIZE) == round+3) {
                    circleOnBoard.setFill(c.getFill());
                }
                anchorPane.getChildren().remove(c);
            });

            anchorPane.getChildren().add(circle);
        }
    }
    private Circle getCircle(double x, double y) {
        for(Node node : anchorPane.getChildren()) {
            if((int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE) == (int) (y/StageProperties.RECTANGLE_SIZE)
                    && (int) (node.getLayoutX()/StageProperties.RECTANGLE_SIZE) == (int) (x/StageProperties.RECTANGLE_SIZE)
                    && (int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE) >= 2
                    && (int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE) <= 5) {
                return node instanceof Circle circle ? circle : null;
            }
        }
        return null;
    }
    private List<Circle> getAllCircles() {
        List<Circle> list = new ArrayList<>();
        for(Node node : anchorPane.getChildren()) {
            if(node instanceof Circle circle &&
                    (int) (node.getLayoutX()/StageProperties.RECTANGLE_SIZE) == round+3 &&
                    (int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE) >= 2 &&
                    (int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE) <= 5) {
                list.add(circle);
            }
        }
        list.sort(Comparator.comparingDouble(Node::getLayoutX));
        return list;
    }
    private void showAnswers() {
        for(Node node : anchorPane.getChildren()) {
            int x = (int) (node.getLayoutX()/StageProperties.RECTANGLE_SIZE);
            int y = (int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE);
            if(node instanceof Circle circle && x == 13 && y >= 2 && y <= 5) {
                circle.setFill(answer[y-2]);
            }
        }
    }
    private List<Circle> getHints() {
        List<Circle> list = new ArrayList<>();
        for(Node node : anchorPane.getChildren()) {
            if(node instanceof Circle circle &&
                    (int) ((node.getLayoutX()+StageProperties.RECTANGLE_SIZE/2)/StageProperties.RECTANGLE_SIZE) == round+3 &&
                    (int) (node.getLayoutY()/StageProperties.RECTANGLE_SIZE) == 1) {
                list.add(circle);
            }
        }
        list.sort(Comparator.comparingDouble(node -> ((Circle) node).getLayoutY())
                .thenComparingDouble(node -> ((Circle) node).getLayoutX()));
        return list;
    }
    @FXML
    private void check() {
        if(gameEnded) {
            return;
        }

        var circles = getAllCircles();
        for(Circle c : circles) {
            if(c.getFill().equals(blankCircleColor)) {return; }
        }

        var hints = getHints();
        int hint = 0;

        ArrayList<Integer> checkFields = new ArrayList<>();
        for(int i=0;i<4;i++) {
            if(circles.get(i).getFill().equals(answer[i])) {
                checkFields.add(i);
                hints.get(hint++).setFill(Color.RED);
            }
        }

        if(hint == 4) {
            showAnswers();
            System.out.println("Wygrałeś");
            borderPane.setBorder(new Border(greenFrame));
            title.getStyleClass().add("green");
            gameEnded = true;
            return;
        }

        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                if(circles.get(i).getFill().equals(answer[j]) && i!=j && !checkFields.contains(i) && !checkFields.contains(j)) {
                    hints.get(hint++).setFill(Color.WHITE);
                    break;
                }
            }
        }
        if(round == 9) {
            showAnswers();
            System.out.println("Przegrałeś");
            borderPane.setBorder(new Border(redFrame));
            title.getStyleClass().add("red");
            gameEnded = true;
            return;
        }
        round++;
    }
    @FXML
    private void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void reset() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mastermindView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StageProperties.STAGE_WIDTH, StageProperties.STAGE_HEIGHT);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(scene);
    }
}
