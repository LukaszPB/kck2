package com.example.projekt2.BattleShip.GameLogic;

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
    public void addShip(Ship s)
    {
        ships.add(s);
    }
    public void removeShip(Ship s) {
        ships.remove(s);
    }
}
