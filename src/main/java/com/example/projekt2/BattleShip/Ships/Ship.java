package com.example.projekt2.BattleShip.Ships;

import com.example.projekt2.BattleShip.Point;
import javafx.scene.shape.Rectangle;

public abstract class Ship {
    protected Point[] cords;
    protected boolean destroyed = false;
    protected int size;

    protected Rectangle rectangle;
    protected double layoutX, layoutY;

    public Ship(int x, int y, int size, char direction)
    {
        cords = new Point[size];
        this.size = size;

        for(int i=0;i<size;i++) {
            switch (direction) {
                case 'r' -> cords[i] = new Point(x++,y);
                case 'l' -> cords[i] = new Point(--x,y);
                case 'u' -> cords[i] = new Point(x,y--);
                case 'd' -> cords[i] = new Point(x,y++);
            }
        }
        rectangle = generateRectangle(direction);
        //addListener(rectangle);
    }
    private Rectangle generateRectangle(char direction) {
        rectangle = new Rectangle();

        int i = direction == 'u' || direction == 'l' ? size-1 : 0;

        rectangle.setLayoutX(cords[i].getY()*30+30);
        rectangle.setLayoutY(cords[i].getX()*30+30);

        if(direction == 'r' || direction == 'l') {
            rectangle.setHeight(30*size);
            rectangle.setWidth(30);
        }
        else {
            rectangle.setHeight(30);
            rectangle.setWidth(30*size);
        }
        rectangle.getStyleClass().add("red-square");

        return rectangle;
    }
    private void addListener(Rectangle rectangle) {
        rectangle.setOnMouseClicked((event)-> {
            layoutX = event.getSceneX();
            layoutY = event.getSceneY();
        });
        rectangle.setOnMouseDragged((event) -> {
            if(event.getSceneX()-layoutX >= 30 && rectangle.getLayoutX() <= 270) {
                rectangle.setLayoutX(rectangle.getLayoutX()+30);
                layoutX = event.getSceneX();
                for(Point p : cords) {
                    p.setX(p.getX()+1);
                }
            }
            if(event.getSceneX()-layoutX <= -30 && rectangle.getLayoutX() >= 60) {
                rectangle.setLayoutX(rectangle.getLayoutX()-30);
                layoutX = event.getSceneX();
                for(Point p : cords) {
                    p.setX(p.getX()-1);
                }
            }
            if(event.getSceneY()-layoutY >= 30 && rectangle.getLayoutY() <= 270) {
                rectangle.setLayoutY(rectangle.getLayoutY()+30);
                layoutY = event.getSceneY();
                for(Point p : cords) {
                    p.setY(p.getY()+1);
                }
            }
            if(event.getSceneY()-layoutY <= -30 && rectangle.getLayoutY() >= 60) {
                rectangle.setLayoutY(rectangle.getLayoutY()-30);
                layoutY = event.getSceneY();
                for(Point p : cords) {
                    p.setY(p.getY()-1);
                }
            }
        });
    }
    public boolean hit(Point p) {
        for(Point point : cords)
        {
            if(p.equals(point)) {
                point.hit();
                destroyed();
                return true;
            }
        }
        return false;
    }
    public void destroyed() {
        for(Point point : cords)
        {
            if(!point.getHit()) {
                return;
            }
        }
        destroyed = true;
    }
    public boolean getDestroyed() { return destroyed; }
    public abstract boolean collision(Ship s);
    public boolean fit(int tableSize) {
        for(Point p : cords)
        {
            if(p.getX() >= tableSize || p.getX() < 0 || p.getY() >= tableSize || p.getY() < 0) {
                return false;
            }
        }
        return true;
    }
    public Point[] getCords() {
        return cords;
    }
    public abstract Ship copy(int x, int y, char d);

    public int getSize() { return size; }

    public boolean wasHit(Point p)
    {
        for(Point point : cords) {
            if(point.equals(p) && point.getHit())
                return true;
        }
        return false;
    }
    public boolean hasPoint(Point p) {
        for(Point point : cords) {
            if(point.equals(p)) {
                return true;
            }
        }
        return false;
    }
    public Rectangle getRectangle() { return rectangle; }
    public double getLayoutX() { return layoutX; }
    public double getLayoutY() { return layoutY; }
    public void setLayoutX(double x) { this.layoutX = x; }
    public void setLayoutY(double y) { this.layoutY = y; }
}
