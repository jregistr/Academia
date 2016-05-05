package com.jeff.game.castlesmack.models.gameplay;


import com.jeff.game.castlesmack.models.items.Cannon;
import com.jeff.game.castlesmack.models.items.House;
import com.jeff.game.castlesmack.models.items.Island;

public class Player {

    public final House house;
    public final Cannon cannon;
    public final Island houseIsland;
    public final Island cannonIsland;

    public final int id;

    public Player(House house, Cannon cannon, Island houseIsland, Island cannonIsland, int id) {
        this.house = house;
        this.cannon = cannon;
        this.houseIsland = houseIsland;
        this.cannonIsland = cannonIsland;
        this.id = id;
    }
}
