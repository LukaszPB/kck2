package com.example.projekt2.BattleShip.GameLogic;

import com.example.projekt2.BattleShip.Point;
import com.example.projekt2.BattleShip.Ships.Ship;

import java.util.ArrayList;
import java.util.Random;

public class Human extends Player {
    private char[] directions = {'r', 'l', 'u', 'd'};
    private Random rand = new Random();

    private ArrayList<Ship> prototypes;
    public Human(ArrayList<Ship> prototypes)
    {
        super();
    }
    public char[][] getMyBoard()
    {
        char[][] tab = new char[tableSize][tableSize];

        for(int i=0;i<tableSize;i++) {
            for(int j=0;j<tableSize;j++) {
                tab[i][j] = ' ';
            }
        }

        for(Ship s : ships) {
            for(Point p : s.getCords()) {
                tab[p.getX()][p.getY()] = 'X';
            }
        }
        return tab;
    }
    public void addShip(Ship s)
    {
        ships.add(s);
    }
}
