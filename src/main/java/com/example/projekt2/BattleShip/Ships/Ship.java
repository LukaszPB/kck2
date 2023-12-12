package com.example.projekt2.BattleShip.Ships;

import com.example.projekt2.BattleShip.Point;

public abstract class Ship {
    protected Point[] cords;
    protected boolean destroyed = false;
    protected int size;

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
}
