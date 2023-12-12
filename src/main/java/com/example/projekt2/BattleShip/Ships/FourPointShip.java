package com.example.projekt2.BattleShip.Ships;

import com.example.projekt2.BattleShip.Point;

import java.util.ArrayList;

public class FourPointShip extends Ship{
    public FourPointShip(int x, int y, char direction) {
        super(x,y,4,direction);
    }
    public FourPointShip() {
        this(0,0,'r');
    }
    public Ship copy(int x, int y, char d) {
        return new FourPointShip(x,y,d);
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
        return "XXXX";
    }
}
