package com.example.projekt2.BattleShip.Ships;

import com.example.projekt2.BattleShip.Point;

import java.util.ArrayList;

public class TwoPointShip extends Ship{
    public TwoPointShip(int x, int y, char direction) {
        super(x,y,2,direction);
    }
    public TwoPointShip() {
        this(0,0,'r');
    }
    public Ship copy(int x, int y, char d) {
        return new TwoPointShip(x,y,d);
    }
    public boolean collision(Ship s)
    {
        ArrayList<Point> points = cords[0].generateNearbyPoints();
        points.addAll(cords[cords.length-1].generateNearbyPoints());
        for(Point p1 : s.cords)
        {
            for(Point p2 : points)
            {
                if(p1.equals(p2)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString()
    {
        return "XX";
    }
}
