package com.example.projekt2.BattleShip.Ships;

import com.example.projekt2.BattleShip.Point;

import java.util.ArrayList;

public class OnePointShip extends Ship{
    public OnePointShip(int x, int y, char direction) {
        super(x,y,1,direction);
    }
    public OnePointShip() {
        this(0,0,'r');
    }
    public Ship copy(int x, int y, char d) {
        return new OnePointShip(x,y,d);
    }
    public boolean collision(Ship s)
    {
        ArrayList<Point> points = cords[0].generateNearbyPoints();
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
        return "X";
    }
}
