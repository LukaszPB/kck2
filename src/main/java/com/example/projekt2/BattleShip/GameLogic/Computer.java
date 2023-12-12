package com.example.projekt2.BattleShip.GameLogic;

import com.example.projekt2.BattleShip.Point;
import com.example.projekt2.BattleShip.Ships.Ship;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player{

    private char[] directions = {'r', 'l', 'u', 'd'};
    private Random rand = new Random();

    private boolean shots[][] = new boolean[tableSize][tableSize];

    private Ship target;
    public Computer(ArrayList<Ship> prototypes)
    {
        super();
        for(Ship s : prototypes)
        {
            generate(s);
        }
    }

    public void setTarget(Ship s) { target = s; }
    private void generate(Ship prototype) {
        int x, y, d;
        Ship ship;

        do {
            x = rand.nextInt(tableSize);
            y = rand.nextInt(tableSize);
            d = rand.nextInt(directions.length);
            ship = prototype.copy(x,y,directions[d]);
        }
        while(!validateShip(ship));
        ships.add(ship);
    }
    public Point shoot(int difficulty)
    {
        int x,y;
        ArrayList<Point> points = new ArrayList<>();

        if(target != null && difficulty > 0)
        {
            for(Point point : target.getCords())
            {
                if(point.getHit()) {
                    points.add(point);
                }
            }

            x = points.get(0).getX();
            y = points.get(0).getY();

            if(points.size() == 1)
            {
                if (!shots[y == 0 ? y : y - 1][x]) {
                    y--;
                } else if (!shots[y][x == 0 ? x : x - 1]) {
                    x--;
                } else if (!shots[y == tableSize-1 ? y : y + 1][x]) {
                    y++;
                } else if (!shots[y][x == tableSize-1 ? x : x + 1]) {
                    x++;
                }
            }
            else
            {
                if(y - points.get(1).getY() == 0)
                {
                    for(Point pp : points)
                    {
                        if(x > pp.getX())
                            x = pp.getX();
                    }

                    if(!shots[y][x == 0 ? x : x - 1]) { x--; }
                    else
                    {
                        for(Point p : points) {
                            if(x < p.getX())
                                x = p.getX();
                        }
                        x++;
                    }
                }
                else
                {
                    for(Point p : points) {
                        if(y > p.getY())
                            y = p.getY();
                    }

                    if (!shots[y == 0 ? y : y - 1][x]) { y--; }
                    else
                    {
                        for(Point pp : points) {
                            if(y < pp.getY())
                                y = pp.getY();
                        }
                        y++;
                    }
                }
            }
        }
        else
        {
            do {
                x = rand.nextInt(tableSize);
                y = rand.nextInt(tableSize);
            }while(shots[y][x]);
        }

        shots[y][x] = true;
        return new Point(x,y);
    }
    public void acknowledgeShipDestruction(Ship s)
    {
        Point[] cords = s.getCords();

        ArrayList<Point> points = cords[0].generateNearbyPoints();
        points.addAll(cords[cords.length-1].generateNearbyPoints());

        int x,y;
        for(Point p : points)
        {
            x = p.getX();
            y = p.getY();

            if(x >= 0 && x < tableSize && y >= 0 && y < tableSize)
                shots[y][x] = true;
        }
    }
}
