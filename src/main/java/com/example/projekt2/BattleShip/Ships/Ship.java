package com.example.projekt2.BattleShip.Ships;

import com.example.projekt2.BattleShip.Point;
import javafx.scene.shape.Rectangle;

public abstract class Ship {
    protected Point[] cords;
    protected boolean destroyed = false;
    protected int size;

    protected Rectangle rectangle;
    protected double layoutX, layoutY;
    protected char direction;
    double height, width;

    public Ship(int x, int y, int size, char direction)
    {
        cords = new Point[size];
        this.size = size;
        this.direction = direction;

        for(int i=0;i<size;i++) {
            switch (direction) {
                case 'r' -> cords[i] = new Point(x++,y);
                case 'l' -> cords[i] = new Point(--x,y);
                case 'u' -> cords[i] = new Point(x,y--);
                case 'd' -> cords[i] = new Point(x,y++);
            }
        }
        rectangle = new Rectangle();
        generateRectangle();
    }
    public void generateRectangle() {

        int i = direction == 'u' || direction == 'l' ? size-1 : 0;

        rectangle.setLayoutX(cords[i].getY()*30+30);
        rectangle.setLayoutY(cords[i].getX()*30+30);

        if(direction == 'r' || direction == 'l') {
            height = 30*size;
            width = 30;
            rectangle.setHeight(height);
            rectangle.setWidth(width);
        }
        else {
            height = 30;
            width = 30*size;
            rectangle.setHeight(height);
            rectangle.setWidth(width);
        }
        rectangle.getStyleClass().add("blue-square");
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

    public char getDirection() {
        return direction;
    }
    public void setCords(Point[] cords) {
        this.cords = cords;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
