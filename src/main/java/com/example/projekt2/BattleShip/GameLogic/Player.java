package com.example.projekt2.BattleShip.GameLogic;


import com.example.projekt2.BattleShip.Point;
import com.example.projekt2.BattleShip.Ships.Ship;

import java.util.ArrayList;

public abstract class Player {
    protected ArrayList<Ship> ships = new ArrayList<>();
    protected int tableSize;

    public Player()
    {
        tableSize = 10;
    }
    public int getTableSize()
    {
        return tableSize;
    }
    public boolean getHit(Point p) {
        for(Ship s : ships) {
            if(s.hit(p)) {
                return true;
            }
        }
        return false;
    }
    public boolean wasHit(Point p) {
        for(Ship s : ships) {
            if(s.wasHit(p)) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Ship> getShips()
    {
        return ships;
    }
    public boolean wasDefeated()
    {
        for(Ship s :ships)
        {
            if(!s.getDestroyed())
                return false;
        }
        return true;
    }
    public Ship getShip(Point p)
    {
        for(Ship s : ships) {
            for(Point point : s.getCords())
            {
                if(point.equals(p))
                    return s;
            }
        }
        return null;
    }
    public boolean validateShip(Ship ship) {
        if (!ship.fit(tableSize)) {
            return false;
        }
        for (Ship s : ships) {
            if (s.collision(ship)) {
                return false;
            }
        }
        return true;
    }
    public boolean hasShipOnPoint(Point p) {
        for(Ship ship : ships) {
            if(ship.hasPoint(p)) {
                return true;
            }

        }
        return false;
    }
}