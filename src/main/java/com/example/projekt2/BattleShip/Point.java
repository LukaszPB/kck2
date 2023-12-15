package com.example.projekt2.BattleShip;

import java.util.ArrayList;
import java.util.Arrays;

public class Point {
    private int x,y;
    private boolean hit;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        hit = false;
    }
    public int getX()  { return x; }
    public int getY() { return y; }
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean getHit() { return hit; }
    public void hit() { hit = true; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }
    public ArrayList<Point> generateNearbyPoints() {
        return new ArrayList<>(Arrays.asList(
                new Point(x-1,y-1), new Point(x-1,y),new Point(x-1,y+1),
                new Point(x,y-1), new Point(x,y),new Point(x,y+1),
                new Point(x+1,y-1), new Point(x+1,y),new Point(x+1,y+1)
        ));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() { return x + ":" + y + ", "; }
}
